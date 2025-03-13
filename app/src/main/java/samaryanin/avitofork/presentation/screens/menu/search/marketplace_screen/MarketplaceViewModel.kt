package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import samaryanin.avitofork.data.database.favorites.FavoriteAdRepository
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val repository: FavoriteAdRepository
) : ViewModel() {
    val allFavorites = repository.allFavorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun addToFavorites(favId: Int) {
        viewModelScope.launch {
            repository.addFavorite(favId)
        }
    }

    /**
     * Удаление объявления из избранного
     */
    fun removeFromFavorites(adId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(adId)
        }
    }
}