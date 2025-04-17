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
        observeFavorites()
    }

    fun observeFavorites() {
        viewModelScope.launch {
            favoriteManager.favorites.collect { ids ->
                _favoriteAdsState.value = UiState.Loading
                try {
                    if (ids.isEmpty()) {
                        _favoriteAdsState.value = UiState.Success(emptyList())
                    } else {
                        val ads = adRepo.getAdsByIds(ids.toList())
                        _favoriteAdsState.value = UiState.Success(ads)
                    }
                } catch (e: Exception) {
                    _favoriteAdsState.value = UiState.Error(e)
                }
            }
        }
    }

    fun toggleFavorite(ad: Ad) {
        favoriteManager.toggleFavorite(ad.id)
        // больше не нужно вызывать getFavoriteAds() вручную
    }
}