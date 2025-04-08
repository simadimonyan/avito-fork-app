package samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.database.cache.FavoriteIds
import samaryanin.avitofork.core.database.cache.FavoriteIds.favIdsFlow
import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import javax.inject.Inject

@Stable
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _favoriteAds = MutableStateFlow<List<Ad>>(emptyList())
    val favoriteAds: StateFlow<List<Ad>> = _favoriteAds.asStateFlow()

    fun getFavoriteAds() {
        viewModelScope.launch {
            if (favIdsFlow.value?.isEmpty() == true) {
                _favoriteAds.value = emptyList()
            } else {
                val ads = adRepo.getAdsByIds(favIdsFlow.value.toList())
                _favoriteAds.value = ads
            }

        }
    }

    fun toggleFavorite(ad: Ad) {
        viewModelScope.launch {
            val current = FavoriteIds.favIdsFlow.value.toMutableSet()
            if (current.contains(ad.id)) {
                current.remove(ad.id)
            } else {
                current.add(ad.id)
            }
            FavoriteIds.favIdsFlow.value = current
            getFavoriteAds()
        }
    }
}