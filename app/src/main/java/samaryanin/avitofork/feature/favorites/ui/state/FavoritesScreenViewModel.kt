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

    private val _favorites = MutableStateFlow<List<Ad>>(emptyList())
    val favorites: StateFlow<List<Ad>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        safeScope.launch {
            favoriteManager.initialize()
            refresh()
        }
    }

    fun toggleFavorite(ad: Ad) = safeScope.launch {
        favoriteManager.toggleFavorite(ad.id)
        favoriteManager.syncWithServer()
        refresh()
    }

    fun refresh() {
        safeScope.launch {
            _isLoading.value = true
            val newFavorites = adRepo.getFavoriteAds()
            _favorites.emitIfChanged(newFavorites)
            _isLoading.value = false
        }
    }
}
