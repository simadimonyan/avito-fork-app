package samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.ui.UiState
import samaryanin.avitofork.core.utils.FavoriteManager
import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import javax.inject.Inject

@Stable
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val adRepo: AdRepo,
    val favoriteManager: FavoriteManager,
) : ViewModel() {

    private val _favoriteAdsState = MutableStateFlow<UiState<List<Ad>>>(UiState.Loading)
    val favoriteAdsState: StateFlow<UiState<List<Ad>>> = _favoriteAdsState

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favoriteAdsState.value = UiState.Loading
            try {
                favoriteManager.loadFromServer()
                val ids = favoriteManager.favorites.value
                val ads = adRepo.getAdsByIds(ids.toList())
                _favoriteAdsState.value = UiState.Success(ads)
            } catch (e: Exception) {
                _favoriteAdsState.value = UiState.Error(e)
            }
        }
    }

    fun toggleFavorite(ad: Ad) {
        favoriteManager.toggleFavorite(ad.id)
        removeFavoriteLocally(ad)

//        viewModelScope.launch {
//            delay(500)
//            loadFavorites()
//        }
    }

    private fun removeFavoriteLocally(ad: Ad) {
        val current = (favoriteAdsState.value as? UiState.Success)?.data?.toMutableList() ?: return
        current.removeAll { it.id == ad.id }
        _favoriteAdsState.value = UiState.Success(current)
    }
}