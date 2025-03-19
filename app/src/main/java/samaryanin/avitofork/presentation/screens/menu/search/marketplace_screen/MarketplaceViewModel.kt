package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import samaryanin.avitofork.data.database.favorites.Ad
import samaryanin.avitofork.data.database.favorites.AdWithFavorite
import samaryanin.avitofork.data.database.favorites.FavoriteAdRepository
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val repository: FavoriteAdRepository
) : ViewModel() {
    val adsWithFavoriteStatus: StateFlow<List<AdWithFavorite>> =
        repository.adsWithFavoriteStatus
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteAds: StateFlow<List<Ad>> =
        repository.allFavorites
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleFavorite(ad: Ad) {
        viewModelScope.launch {
            if (adsWithFavoriteStatus.value.any { it.ad.id == ad.id && it.isFavorite }) {
                repository.removeFavorite(ad.id)
            } else {
                repository.addFavorite(ad.id)
            }
        }
    }

    fun addAds(ads: List<Ad>) {
        viewModelScope.launch {
            repository.insertAds(ads)
        }
    }
}

//}
//    val allFavorites = repository.allFavorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
//
//    fun addToFavorites(favId: Int) {
//        viewModelScope.launch {
//            repository.addFavorite(favId)
//        }
//    }
//
//    /**
//     * Удаление объявления из избранного
//     */
//    fun removeFromFavorites(adId: Int) {
//        viewModelScope.launch {
//            repository.removeFavorite(adId)
//        }
//    }
//}