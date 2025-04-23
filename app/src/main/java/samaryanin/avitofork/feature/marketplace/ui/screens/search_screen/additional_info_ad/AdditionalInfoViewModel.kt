package samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.additional_info_ad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.navigation.NavigationHolder

@HiltViewModel
class AdditionalInfoViewModel
@javax.inject.Inject constructor(
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _adById = MutableStateFlow<Ad?>(null)
    val adById: MutableStateFlow<Ad?> get() = _adById

    init {
        NavigationHolder.id?.let { getAdById(it) }

    }

    fun getAdById(adId: String) {
        viewModelScope.launch {
            _adById.value = adRepo.getAdById(adId)
        }
    }
}