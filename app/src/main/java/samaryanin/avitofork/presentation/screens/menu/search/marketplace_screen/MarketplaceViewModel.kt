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
import samaryanin.avitofork.data.cache.FavoriteIds.favIdsFlow
import samaryanin.avitofork.data.models.favorites.AdEntity
import samaryanin.avitofork.data.models.favorites.AdWithFavorite
import samaryanin.avitofork.data.repository.database.FavoriteAdRepository
import samaryanin.avitofork.data.repository.network.AdRepo
import samaryanin.avitofork.domain.model.favorites.Ad
import samaryanin.avitofork.domain.model.favorites.Category
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val repository: FavoriteAdRepository,
    private val adRepo: AdRepo,
) : ViewModel() {

    val favoriteIds: StateFlow<Set<String>> = favIdsFlow.asStateFlow()

    private val _allAds = MutableStateFlow<List<Ad>?>(null)
    val allAds = _allAds.asStateFlow()

    private val _allCategories = MutableStateFlow<List<Category>?>(null)
    val allCategories = _allCategories.asStateFlow()

    val selectedCategoryIds = MutableStateFlow<List<String>>(emptyList())

    init {
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

    private val adsWithFavoriteStatus: StateFlow<List<AdWithFavorite>> =
        repository.getAdsWithFavoriteStatus()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addToFav(adId: String) {
        val updatedIds = favoriteIds.value.toMutableSet()
        if (updatedIds.contains(adId)) {
            updatedIds.remove(adId)
        } else {
            updatedIds.add(adId)
        }
        favIdsFlow.value = updatedIds
    }

//    fun toggleFavoriteForDB(ad: AdEntity) {
//        viewModelScope.launch {
//            val isCurrentlyFavorite = _favoriteAds.value.any { it.id == ad.id }
//            if (isCurrentlyFavorite) {
//                repository.removeFavorite(ad.id)
//                _favoriteAds.value = _favoriteAds.value.filter { it.id != ad.id }
//            } else {
//                repository.addFavorite(ad.id)
//                _favoriteAds.value = _favoriteAds.value + ad
//            }
//        }
//    }

    fun addAds(ads: List<AdEntity>) {
        viewModelScope.launch {
            repository.insertAds(ads)
        }
    }
}