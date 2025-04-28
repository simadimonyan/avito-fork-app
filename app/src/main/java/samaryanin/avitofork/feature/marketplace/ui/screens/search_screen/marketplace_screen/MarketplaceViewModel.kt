package samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.database.cache.CacheManager
import samaryanin.avitofork.core.ui.UiState
import samaryanin.avitofork.core.utils.FavoriteManager
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Category
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.GetAllCategoriesUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.GetFilteredAdsUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.GetImageBytesByIdUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.ToggleFavoriteAdUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val getFilteredAdsUseCase: GetFilteredAdsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val toggleFavoriteAdUseCase: ToggleFavoriteAdUseCase,
    private val downloadImageUseCase: GetImageBytesByIdUseCase,
    private val favoriteManager: FavoriteManager,
    private var cacheManager: CacheManager
) : ViewModel() {

    val allAds = MutableStateFlow<List<Ad>?>(null)
    val allCategories = MutableStateFlow<List<Category>?>(null)
    val selectedCategoryIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds = favoriteManager.favorites

    val adsState = MutableStateFlow<UiState<List<Ad>>>(UiState.Loading)
    val categoriesState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)

    val isAuthorized = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            try {
                favoriteManager.loadFromServer()
            } catch (e: Exception) {

            }
            // всегда актуальные избранные с сервера
        }

        isAuthorized.value = cacheManager.preferences.getString("authToken", null) != null

        viewModelScope.launch {
            selectedCategoryIds
                .debounce(250.milliseconds)
                .collectLatest { ids ->
                    adsState.value = UiState.Loading
                    try {
                        val result = getFilteredAdsUseCase(ids)
                        adsState.value = UiState.Success(result)
                    } catch (e: Exception) {
                        adsState.value = UiState.Error(e)
                    }
                }
        }

        viewModelScope.launch {
            categoriesState.value = UiState.Loading
            try {
                val result = getAllCategoriesUseCase()
                categoriesState.value = UiState.Success(result)
                allCategories.value = result
            } catch (e: Exception) {
                categoriesState.value = UiState.Error(e)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                adsState.value = UiState.Loading
                favoriteManager.loadFromServer()
                val result = getFilteredAdsUseCase(selectedCategoryIds.value)
                adsState.value = UiState.Success(result)
            } catch (e: Exception) {
                adsState.value = UiState.Error(e)
            }
        }
    }

    fun toggleFavoriteAd(id: String) {
        viewModelScope.launch {
            favoriteManager.toggleFavorite(id) // автоматически отправляется на сервер
        }
    }

    fun isFavorite(id: String): Boolean {
        return favoriteManager.isFavorite(id)
    }
}
