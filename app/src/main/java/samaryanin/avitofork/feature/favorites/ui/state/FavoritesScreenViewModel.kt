package samaryanin.avitofork.feature.favorites.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.favorites.data.FavoriteManager
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import samaryanin.avitofork.shared.extensions.emitIfChanged
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import javax.inject.Inject

@Stable
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val adRepo: AdRepo,
    private val favoriteManager: FavoriteManager,
) : ViewModel() {

    private val _ads = MutableStateFlow<List<Ad>>(emptyList())
    val ads: StateFlow<List<Ad>> = _ads.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        safeScope.launch {
            favoriteManager.syncWithServer()
            loadFavorites(showLoading = false)
        }
    }

    fun refresh() = safeScope.launch {
        loadFavorites(showLoading = true)
    }

    fun syncFavorites() = safeScope.launch {
        favoriteManager.syncWithServer()
        loadFavorites(showLoading = false)
    }

    fun toggleFavorite(ad: Ad) = safeScope.launch {
        favoriteManager.toggleFavorite(ad.id)
        favoriteManager.syncWithServer()
        loadFavorites(showLoading = false)
    }

    private suspend fun loadFavorites(showLoading: Boolean) {
        if (showLoading) _isLoading.value = true

        val favoriteIds = favoriteManager.favorites.value.toList()
        val newFavorites = if (favoriteIds.isEmpty()) {
            emptyList()
        } else {
            adRepo.getFavoriteAds()
        }

        _ads.emitIfChanged(newFavorites)
        if (showLoading) _isLoading.value = false
    }
}