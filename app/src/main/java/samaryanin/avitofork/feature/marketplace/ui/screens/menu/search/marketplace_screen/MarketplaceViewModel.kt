package samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.database.cache.FavoriteIds.favIdsFlow
import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Category
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
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

    fun addToFav(adId: String) {
        val updatedIds = favoriteIds.value.toMutableSet()
        if (updatedIds.contains(adId)) {
            updatedIds.remove(adId)
        } else {
            updatedIds.add(adId)
        }
        favIdsFlow.value = updatedIds
    }
}