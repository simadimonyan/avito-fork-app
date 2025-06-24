package samaryanin.avitofork.feature.favorites.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.favorites.data.FavoriteManager
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import samaryanin.avitofork.shared.state.network.NetworkState
import javax.inject.Inject

@Stable
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val adRepo: AdRepo,
    val favoriteManager: FavoriteManager,
) : ViewModel() {

    private val _favoriteAdsState = MutableStateFlow<NetworkState<List<Ad>>>(NetworkState.Loading)
    val favoriteAdsState: StateFlow<NetworkState<List<Ad>>> = _favoriteAdsState

    init {
        viewModelScope.launch {
            favoriteManager.initialize()
            loadFavorites()
        }
    }

    suspend fun loadFavorites() {
        _favoriteAdsState.value = NetworkState.Loading
        try {
            val ids = favoriteManager.favorites.value
            val ads = adRepo.getAdsByIds(ids.toList())
            _favoriteAdsState.value = NetworkState.Success(ads)
        } catch (e: Exception) {
            _favoriteAdsState.value = NetworkState.Error(e)
        }
    }

    fun toggleFavorite(ad: Ad) {
        favoriteManager.toggleFavorite(ad.id)
        removeAd(ad.id)
        safeScope.launch {
            favoriteManager.syncWithServer()
        }
    }

    private fun removeAd(id: String) {
        val current =
            (_favoriteAdsState.value as? NetworkState.Success)?.data?.toMutableList() ?: return
        current.removeAll { it.id == id }
        _favoriteAdsState.value = NetworkState.Success(current)
    }
}