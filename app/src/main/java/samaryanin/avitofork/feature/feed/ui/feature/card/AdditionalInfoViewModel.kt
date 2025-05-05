package samaryanin.avitofork.feature.feed.ui.feature.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import javax.inject.Inject

@HiltViewModel
class AdditionalInfoViewModel
@Inject constructor(
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _adById = MutableStateFlow<Ad?>(null)
    val adById: MutableStateFlow<Ad?> get() = _adById

    init {
        NavigationHolder.id?.let { getAdById(it) }

    }

    fun getAdById(adId: String) {
        viewModelScope.launch {
            //_adById.value = adRepo.getAdById(adId)
        }
    }
}