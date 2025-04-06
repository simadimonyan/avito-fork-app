package samaryanin.avitofork.presentation.screens.menu.favorites

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.data.cache.FavoriteIds
import samaryanin.avitofork.data.cache.FavoriteIds.favIdsFlow
import samaryanin.avitofork.data.database.favorites.FavoriteAdRepository
import samaryanin.avitofork.data.network.repository.AdRepo
import samaryanin.avitofork.domain.model.Ad
import javax.inject.Inject

@Stable
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val repository: FavoriteAdRepository,
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _favoriteAds = MutableStateFlow<List<Ad>>(emptyList())
    val favoriteAds: StateFlow<List<Ad>> = _favoriteAds.asStateFlow()

    fun getFavoriteAds() {
        viewModelScope.launch {
            if (favIdsFlow.value?.isEmpty() == true) {
                _favoriteAds.value = emptyList()
            } else {
                val ads = adRepo.getAdsByIds(favIdsFlow.value.toList())
                _favoriteAds.value = ads
            }

        }
    }

    fun toggleFavorite(ad: Ad) {
        viewModelScope.launch {
            val current = FavoriteIds.favIdsFlow.value.toMutableSet()
            if (current.contains(ad.id)) {
                current.remove(ad.id)
            } else {
                current.add(ad.id)
            }
            FavoriteIds.favIdsFlow.value = current
            getFavoriteAds()
        }
    }
}
// Метод для обновления списка избранных объявлений
//    fun refreshFavoriteAds() {
//        viewModelScope.launch {
//            repository.getFavoriteAds().collect { ads ->
//                _favoriteAds.value = ads // Обновляем состояние с новыми данными
//            }
//        }
//    }
//
//    // Метод для переключения избранного объявления
//    fun toggleFavorite(ad: AdEntity) {
//        viewModelScope.launch {
//            if (_favoriteAds.value.any { it.id == ad.id }) {
//                repository.removeFavorite(ad.id)
//            } else {
//                repository.addFavorite(ad.id)
//            }
//            refreshFavoriteAds() // Перезагружаем список после изменения
//        }
//    }
