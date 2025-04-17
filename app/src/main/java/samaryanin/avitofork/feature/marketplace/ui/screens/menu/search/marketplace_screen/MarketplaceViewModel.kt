package samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.ui.UiState
import samaryanin.avitofork.core.utils.FavoriteManager
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Category
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.GetAllCategoriesUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.GetFilteredAdsUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.ToggleFavoriteAdUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val getFilteredAdsUseCase: GetFilteredAdsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val toggleFavoriteAdUseCase: ToggleFavoriteAdUseCase,
    private val favoriteManager: FavoriteManager,
) : ViewModel() {

    val allAds = MutableStateFlow<List<Ad>?>(null)
    val allCategories = MutableStateFlow<List<Category>?>(null)
    val selectedCategoryIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds = favoriteManager.favorites

    val adsState = MutableStateFlow<UiState<List<Ad>>>(UiState.Loading)
    val categoriesState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)

    init {
        loadFavorites()

        if (favoriteIds.value.isNotEmpty()) {
            viewModelScope.launch {
                favoriteManager.syncWithServer()
            }
        }

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
            } catch (e: Exception) {
                categoriesState.value = UiState.Error(e)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                adsState.value = UiState.Loading
                val result = getFilteredAdsUseCase(selectedCategoryIds.value)
                adsState.value = UiState.Success(result)
            } catch (e: Exception) {
                adsState.value = UiState.Error(e)
            }
        }
    }

    // Добавить/удалить товар из избранного
    fun toggleFavoriteAd(id: String) {
        favoriteManager.toggleFavorite(id)
        viewModelScope.launch {
            try{
                toggleFavoriteAdUseCase.invoke(id, favoriteManager.isFavorite(id))

            } catch (e: Exception){

            }
        }
    }

    fun isFavorite(id: String): Boolean {
        return favoriteManager.isFavorite(id)
    }

    // Загрузка избранных товаров из хранилища
    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteManager.loadFavorites()
        }
    }
}