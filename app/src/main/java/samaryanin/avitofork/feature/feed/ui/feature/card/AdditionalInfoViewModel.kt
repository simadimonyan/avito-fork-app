package samaryanin.avitofork.feature.feed.ui.feature.card

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import ru.dimagor555.avito.category.domain.field.FieldData
import ru.dimagor555.avito.category.domain.field.FieldDefinition
import ru.dimagor555.avito.category.domain.tree.CategoryTree
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.favorites.domain.usecases.PostSetViewUseCase
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import samaryanin.avitofork.feature.poster.domain.usecases.ConfigurationUseCase
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AdditionalInfoViewModel @Inject constructor(
    private val adRepo: AdRepo,
    private val configurationUseCase: ConfigurationUseCase,
    private val postSetViewUseCase: PostSetViewUseCase,
    ) : ViewModel() {

    private val _adById = MutableStateFlow<Ad?>(null)
    val adById: StateFlow<Ad?> = _adById.asStateFlow()

    private val _latitude = MutableStateFlow<Double?>(null)
    val latitude: StateFlow<Double?> = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow<Double?>(null)
    val longitude: StateFlow<Double?> = _longitude.asStateFlow()

    private val _fieldDefinitionsMap = MutableStateFlow<Map<String, FieldDefinition>>(emptyMap())
    val fieldDefinitionsMap: StateFlow<Map<String, FieldDefinition>> = _fieldDefinitionsMap.asStateFlow()

    private val _baseAddress = MutableStateFlow<String?>(null)
    val baseAddress: StateFlow<String?> = _baseAddress.asStateFlow()

    val ignoredFields = setOf("Изображения", "Заголовок", "Описание","Адрес", "Цена")

    init {
        safeScope.launch {
            val categoryTree = configurationUseCase.getCategoryTree()
            _fieldDefinitionsMap.value = categoryTree.getAllFieldDefinitionsMap()
            NavigationHolder.id?.let {
                getAdById(it)
                postSetViewUseCase(it)
            }
        }
    }

    private fun getAdById(adId: String) {
        safeScope.launch {
            val ad = adRepo.getAdById(adId)
            _adById.value = ad

            if (ad != null) {
                ad.fieldValues.firstOrNull { it.fieldId == "base_address" }?.let { fieldValue ->
                    val addressValue = fieldValue.fieldData as? FieldData.AddressValue
                    _baseAddress.value = addressValue?.fullText

                    _latitude.value = addressValue?.latitude
                    _longitude.value = addressValue?.longitude
                }
            }
        }
    }

    fun loadLatLon(context: Context, address: String) {
        safeScope.launch {
            val coords = geocodeAddress(context, address)
            coords?.let { (lat, lon) ->
                _latitude.value = lat
                _longitude.value = lon
            }
        }
    }

    private suspend fun geocodeAddress(context: Context, address: String): Pair<Double, Double>? {
        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val result = geocoder.getFromLocationName(address, 1)
                result?.firstOrNull()?.let { Pair(it.latitude, it.longitude) }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun CategoryTree.getAllFieldDefinitionsMap(): Map<String, FieldDefinition> {
        val map = mutableMapOf<String, FieldDefinition>()
        all().forEach { category ->
            category.ownFieldDefinitions.forEach { field ->
                map[field.id] = field
            }
        }
        return map
    }
}