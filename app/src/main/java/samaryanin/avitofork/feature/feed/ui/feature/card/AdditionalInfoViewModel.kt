package samaryanin.avitofork.feature.feed.ui.feature.card

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AdditionalInfoViewModel @Inject constructor(
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _adById = MutableStateFlow<Ad?>(null)
    val adById: StateFlow<Ad?> = _adById

    private val _latitude = MutableStateFlow<Double?>(null)
    val latitude: StateFlow<Double?> = _latitude

    private val _longitude = MutableStateFlow<Double?>(null)
    val longitude: StateFlow<Double?> = _longitude

    init {
        NavigationHolder.id?.let { getAdById(it) }
    }

    fun getAdById(adId: String) {
        viewModelScope.launch {
            _adById.value = adRepo.getAdById(adId)
        }
    }

    fun loadLatLon(context: Context, address: String) {
        viewModelScope.launch {
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
                result?.firstOrNull()?.let {
                    Pair(it.latitude, it.longitude)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}