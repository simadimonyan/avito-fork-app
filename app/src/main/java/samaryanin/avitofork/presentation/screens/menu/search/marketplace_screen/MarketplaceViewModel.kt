package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _favoriteAds = MutableStateFlow<List<Ad>>(emptyList())
    val favoriteAds: StateFlow<List<Ad>> get() = _favoriteAds

    init {
        // Инициализация с загрузкой избранных данных
        refreshFavoriteAds()
    }

    // Метод для обновления списка избранных объявлений
    fun refreshFavoriteAds() {
        viewModelScope.launch {
            repository.getFavoriteAds().collect { ads ->
                _favoriteAds.value = ads // Обновляем состояние с новыми данными
            }
        }
    }

    val allAds: StateFlow<List<Ad>> = repository.getAllAds()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val adsWithFavoriteStatus: StateFlow<List<AdWithFavorite>> =
        repository.getAdsWithFavoriteStatus()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleFavorite(ad: Ad) {
        viewModelScope.launch {
            val isCurrentlyFavorite = _favoriteAds.value.any { it.id == ad.id }
            if (isCurrentlyFavorite) {
                repository.removeFavorite(ad.id)
                _favoriteAds.value = _favoriteAds.value.filter { it.id != ad.id }
            } else {
                repository.addFavorite(ad.id)
                _favoriteAds.value = _favoriteAds.value + ad
            }
        }
    }

    fun addAds(ads: List<Ad>) {
        viewModelScope.launch {
            repository.insertAds(ads)
        }
    }
}