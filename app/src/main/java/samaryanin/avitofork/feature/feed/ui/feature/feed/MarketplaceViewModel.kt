package samaryanin.avitofork.feature.feed.ui.feature.feed

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.favorites.data.FavoriteManager
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.favorites.domain.models.Category
import samaryanin.avitofork.feature.favorites.domain.usecases.GetAllCategoriesUseCase
import samaryanin.avitofork.feature.favorites.domain.usecases.GetFilteredAdsUseCase
import samaryanin.avitofork.feature.favorites.domain.usecases.GetSearchedAdUseCase
import samaryanin.avitofork.shared.extensions.emitIfChanged
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val getFilteredAdsUseCase: GetFilteredAdsUseCase,
    private val getSearchedAdUseCase: GetSearchedAdUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val favoriteManager: FavoriteManager,
    cacheManager: CacheManager,
    val appStateHolder: AppStateHolder
) : ViewModel() {

    private val _ads = MutableStateFlow<List<Ad>>(emptyList())
    val ads: StateFlow<List<Ad>> = _ads.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    val selectedCategoryIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds: StateFlow<Set<String>> = favoriteManager.favorites
//    val isAuthorized: StateFlow<Boolean> =
//        MutableStateFlow(cacheManager.preferences.getString("authToken", null) != null)

    private var searchJob: Job? = null

    init {
        safeScope.launch {
            val data = getAllCategoriesUseCase()
            _categories.emitIfChanged(data)
        }

        safeScope.launch {
            selectedCategoryIds
                .debounce(250.milliseconds)
                .collectLatest { ids ->
                    loadAds { getFilteredAdsUseCase(ids) }
                }
        }

        safeScope.launch {
            favoriteManager.syncWithServer()
            loadAds { getFilteredAdsUseCase(selectedCategoryIds.value) }
        }
    }

    fun refresh() = safeScope.launch {
        loadAds { getFilteredAdsUseCase(selectedCategoryIds.value) }
    }

    fun search(text: String) {
        searchJob?.cancel()

        if (text.isBlank()) {
            refresh(); return
        }

        searchJob = safeScope.launch {
            loadAds {
                delay(500)
                getSearchedAdUseCase(text)
            }
        }
    }

    fun syncFavorites() = safeScope.launch {
        favoriteManager.syncWithServer()
    }

    fun toggleFavoriteAd(id: String) = safeScope.launch {
        favoriteManager.toggleFavorite(id)
    }

    private suspend fun loadAds(block: suspend () -> List<Ad>) {
        _isLoading.value = true
        val newAds = runCatching { block() }.getOrDefault(emptyList())
        _ads.emitIfChanged(newAds)
        _isLoading.value = false
    }
}