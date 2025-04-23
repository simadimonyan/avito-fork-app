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
                val ads = if (ids.isEmpty()) emptyList() else adRepo.getAdsByIds(ids.toList())
                _favoriteAdsState.value = UiState.Success(ads)
            } catch (e: Exception) {
                _favoriteAdsState.value = UiState.Error(e)
            }
        }
    }

    fun toggleFavorite(ad: Ad) {
        viewModelScope.launch {
            favoriteManager.toggleFavorite(ad.id)
            loadFavorites()
        }
    }
}