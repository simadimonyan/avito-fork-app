package samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
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
    private val favoriteManager: FavoriteManager
) : ViewModel() {

    val allAds = MutableStateFlow<List<Ad>?>(null)
    val allCategories = MutableStateFlow<List<Category>?>(null)
    val selectedCategoryIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds = favoriteManager.favorites

    init {
        // Загрузка избранных товаров из DataStore
        loadFavorites()

        // Синхронизация избранных товаров с сервером
        viewModelScope.launch {
      //      favoriteManager.syncWithServer()
        }

        viewModelScope.launch {
            // Обработка изменения выбранных категорий
            selectedCategoryIds
                .debounce(250.milliseconds)
                .collectLatest { ids ->
                    allAds.value = getFilteredAdsUseCase(ids)
                }
        }

        viewModelScope.launch {
            // Загрузка всех категорий
            allCategories.value = getAllCategoriesUseCase()
        }
    }

    // Добавить/удалить товар из избранного
    fun toggleFavoriteAd(id: String) {
        favoriteManager.toggleFavorite(id)
        viewModelScope.launch {
            toggleFavoriteAdUseCase.invoke(id, favoriteManager.isFavorite(id))
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