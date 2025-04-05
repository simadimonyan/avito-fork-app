package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import samaryanin.avitofork.data.database.favorites.AdEntity
import samaryanin.avitofork.data.database.favorites.AdWithFavorite
import samaryanin.avitofork.data.database.favorites.FavoriteAdRepository
import samaryanin.avitofork.data.network.repository.AdRepo
import samaryanin.avitofork.domain.model.Ad
import samaryanin.avitofork.domain.model.Category
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val repository: FavoriteAdRepository,
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _favoriteAds = MutableStateFlow<List<AdEntity>>(emptyList())
    val favoriteAds: StateFlow<List<AdEntity>> get() = _favoriteAds

    private val _allAds = MutableStateFlow<List<Ad>?>(null)
    val allAds = _allAds.asStateFlow()

    private val _allCategories = MutableStateFlow<List<Category>?>(null)
    val allCategories = _allCategories.asStateFlow()

    val selectedCategoryIds = MutableStateFlow<List<String>>(emptyList())

    init {
        // Инициализация с загрузкой избранных данных
        refreshFavoriteAds()
        viewModelScope.launch {
            selectedCategoryIds
                .onEach {
                    _allAds.value = adRepo.getFilteredAds(it)
                }
                .debounce(250.milliseconds)
                .collect()
        }
        viewModelScope.launch {
            _allCategories.value = adRepo.getAllCategories()
        }
    }

    // Метод для обновления списка избранных объявлений
    fun refreshFavoriteAds() {
        viewModelScope.launch {
            repository.getFavoriteAds().collect { ads ->
                _favoriteAds.value = ads // Обновляем состояние с новыми данными
            }
        }
    }

    private val adsWithFavoriteStatus: StateFlow<List<AdWithFavorite>> =
        repository.getAdsWithFavoriteStatus()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    fun toggleFavoriteForDB(ad: AdEntity) {
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

    fun addAds(ads: List<AdEntity>) {
        viewModelScope.launch {
            repository.insertAds(ads)
        }
    }
}