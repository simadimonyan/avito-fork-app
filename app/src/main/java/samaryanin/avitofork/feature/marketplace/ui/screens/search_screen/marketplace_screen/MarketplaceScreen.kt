package samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.ui.UiState
import samaryanin.avitofork.core.ui.utils.components.ShimmerAdCard
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.theme.AvitoForkTheme
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen.marketplace_items.SearchBar
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen.marketplace_items.additional_categories.CategoriesWithPhotos
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen.marketplace_items.main_categories.SelectableLazyRow
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen.marketplace_items.product.ProductCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MarketplaceScreen(globalNavController: NavHostController) {

    var search by remember { mutableStateOf("") }
    val viewModel: MarketplaceViewModel = hiltViewModel()
    val categories by viewModel.allCategories.collectAsState()
    val selectedCategoryIds by viewModel.selectedCategoryIds.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    val adsState by viewModel.adsState.collectAsState()

    val lazyGridState = rememberLazyGridState()
    val showShadow by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 0 } }

    val refreshingState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val isRefreshing = adsState is UiState.Loading

    val ads = when (adsState) {
        is UiState.Success -> (adsState as UiState.Success<List<Ad>>).data
        is UiState.Loading -> (adsState as? UiState.Success<List<Ad>>)?.data ?: emptyList()
        else -> emptyList()
    }
    val showShimmer = adsState is UiState.Loading && ads.isEmpty()
    val showError = adsState is UiState.Error && ads.isEmpty()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    AvitoForkTheme {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = refreshingState,
            onRefresh = {
                coroutineScope.launch { viewModel.refresh() }
            }
        ) {
            Scaffold(
                containerColor = Color.White
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyVerticalGrid(
                        state = lazyGridState,
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 50.dp),
                        contentPadding = PaddingValues(bottom = 50.dp)
                    ) {
                        item { Space(40.dp) }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            CategoriesWithPhotos(
                                categories = categories.orEmpty(),
                                selectedCategoryIds = selectedCategoryIds,
                                onSelectedCategoriesIdsChange = {
                                    viewModel.selectedCategoryIds.value = it
                                }
                            )
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            SelectableLazyRow()
                        }

                        if (showShimmer) {
                            items(8) {
                                ShimmerAdCard(
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        } else {
                            items(ads) { ad ->
                                ProductCard(
                                    ad = ad,
                                    isFav = viewModel.isFavorite(ad.id),
                                    globalNavController = globalNavController,
                                    onFavoriteClick = {
                                        viewModel.toggleFavoriteAd(ad.id)
                                    },
                                    isAuthorized = viewModel.isAuthorized
                                )
                            }
                        }

                        if (showError) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Ошибка загрузки: ${(adsState as UiState.Error).exception.message}",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Red
                                )
                            }
                        }
                    }

                    SearchBar(
                        search = search,
                        onSearchChange = { search = it },
                        showShadow = showShadow
                    )
                }
            }
        }
    }
}