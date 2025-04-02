package samaryanin.avitofork.presentation.screens.menu.favorites

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.data.database.favorites.Ad
import samaryanin.avitofork.data.database.favorites.FavoriteAdRepository
import javax.inject.Inject

@Stable
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val repository: FavoriteAdRepository
) : ViewModel() {

    private val _favoriteAds = MutableStateFlow<List<Ad>>(emptyList())
    val favoriteAds: StateFlow<List<Ad>> get() = _favoriteAds

    // Инициализация с загрузкой избранных данных
    init {
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

    // Метод для переключения избранного объявления
    fun toggleFavorite(ad: Ad) {
        viewModelScope.launch {
            if (_favoriteAds.value.any { it.id == ad.id }) {
                repository.removeFavorite(ad.id)
            } else {
                repository.addFavorite(ad.id)
            }
            refreshFavoriteAds() // Перезагружаем список после изменения
        }
    }
}