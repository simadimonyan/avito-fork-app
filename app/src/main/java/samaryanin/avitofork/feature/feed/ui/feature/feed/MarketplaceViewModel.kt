package samaryanin.avitofork.feature.feed.ui.feature.feed

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.favorites.data.FavoriteManager
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.favorites.domain.models.Category
import samaryanin.avitofork.feature.favorites.domain.usecases.GetAllCategoriesUseCase
import samaryanin.avitofork.feature.favorites.domain.usecases.GetFilteredAdsUseCase
import samaryanin.avitofork.feature.favorites.domain.usecases.GetImageBytesByIdUseCase
import samaryanin.avitofork.feature.favorites.domain.usecases.ToggleFavoriteAdUseCase
import samaryanin.avitofork.shared.state.network.NetworkState
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

    val adsState = MutableStateFlow<NetworkState<List<Ad>>>(NetworkState.Loading)
    val categoriesState = MutableStateFlow<NetworkState<List<Category>>>(NetworkState.Loading)

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
                    adsState.value = NetworkState.Loading
                    try {
                        val result = getFilteredAdsUseCase(ids)
                        adsState.value = NetworkState.Success(result)
                    } catch (e: Exception) {
                        adsState.value = NetworkState.Error(e)
                    }
                }
        }

        viewModelScope.launch {
            categoriesState.value = NetworkState.Loading
            try {
                val result = getAllCategoriesUseCase()
                categoriesState.value = NetworkState.Success(result)
                allCategories.value = result
            } catch (e: Exception) {
                categoriesState.value = NetworkState.Error(e)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                adsState.value = NetworkState.Loading
                favoriteManager.loadFromServer()
                val result = getFilteredAdsUseCase(selectedCategoryIds.value)
                adsState.value = NetworkState.Success(result)
            } catch (e: Exception) {
                adsState.value = NetworkState.Error(e)
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