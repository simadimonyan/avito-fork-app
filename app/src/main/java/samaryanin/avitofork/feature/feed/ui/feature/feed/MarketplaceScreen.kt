package samaryanin.avitofork.feature.feed.ui.feature.feed

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
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.ui.feature.card.components.CategoriesWithPhotos
import samaryanin.avitofork.feature.feed.ui.feature.card.components.SelectableLazyRow
import samaryanin.avitofork.feature.feed.ui.feature.feed.components.ProductCard
import samaryanin.avitofork.feature.feed.ui.shared.SearchBar
import samaryanin.avitofork.shared.state.network.NetworkState
import samaryanin.avitofork.shared.ui.components.ShimmerAdCard
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.AvitoForkTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MarketplaceScreen(globalNavController: NavHostController) {

    var searchText by remember { mutableStateOf("") }
    val viewModel: MarketplaceViewModel = hiltViewModel()
    val categories by viewModel.allCategories.collectAsState()
    val selectedCategoryIds by viewModel.selectedCategoryIds.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    val adsState by viewModel.adsState.collectAsState()

    val lazyGridState = rememberLazyGridState()
    val showShadow by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 0 } }

    val refreshingState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val isRefreshing = adsState is NetworkState.Loading

    val ads = when (adsState) {
        is NetworkState.Success -> (adsState as NetworkState.Success<List<Ad>>).data
        is NetworkState.Loading -> (adsState as? NetworkState.Success<List<Ad>>)?.data ?: emptyList()
        else -> emptyList()
    }
    val showShimmer = adsState is NetworkState.Loading && ads.isEmpty()
    val showError = adsState is NetworkState.Error && ads.isEmpty()

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
                                    text = "Ошибка загрузки: ${(adsState as NetworkState.Error).exception.message}",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Red
                                )
                            }
                        }
                    }

                    SearchBar(
                        search = searchText,
                        onSearchChange = {
                            searchText = it
                            viewModel.search(searchText)            },
                        showShadow = showShadow
                    )
                }
            }
        }
    }
}