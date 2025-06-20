package samaryanin.avitofork.feature.feed.ui.feature.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
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
import samaryanin.avitofork.feature.feed.ui.feature.card.components.CategoriesWithPhotos
import samaryanin.avitofork.feature.feed.ui.feature.card.components.SelectableLazyRow
import samaryanin.avitofork.feature.feed.ui.feature.feed.components.ProductCard
import samaryanin.avitofork.feature.feed.ui.shared.SearchBar
import samaryanin.avitofork.shared.ui.components.ShimmerAdCard
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.AvitoForkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(globalNavController: NavHostController) {

    val vm: MarketplaceViewModel = hiltViewModel()

    val ads by vm.ads.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val categories by vm.categories.collectAsState()
    val selectedIds by vm.selectedCategoryIds.collectAsState()
    val favoriteIds by vm.favoriteIds.collectAsState()
    val appState by vm.appStateStore.appState.appState.collectAsState()

    var searchText by remember { mutableStateOf("") }

    val pullState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()

    val gridState = rememberLazyGridState()
    val showShadow by remember { derivedStateOf { gridState.firstVisibleItemIndex > 0 } }

    AvitoForkTheme {
        PullToRefreshBox(
            isRefreshing = isLoading,
            state = pullState,
            onRefresh = { scope.launch { vm.refresh() } }
        ) {
            Scaffold(containerColor = Color.White) { paddings ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(paddings)
                ) {
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        contentPadding = PaddingValues(bottom = 50.dp)
                    ) {
                        item { Space(40.dp) }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            CategoriesWithPhotos(
                                categories = categories,
                                selectedCategoryIds = selectedIds,
                                onSelectedCategoriesIdsChange = {
                                    vm.selectedCategoryIds.value = it
                                }
                            )
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) { SelectableLazyRow() }

                        if (isLoading && ads.isEmpty()) {
                            items(8) { ShimmerAdCard(Modifier.padding(8.dp)) }
                        } else {
                            items(
                                items = ads,
                                key = { it.id }
                            ) { ad ->
                                val isFav by remember(favoriteIds, ad.id) {
                                    derivedStateOf { ad.id in favoriteIds }
                                }
                                ProductCard(
                                    ad = ad,
                                    isFav = isFav,
                                    globalNavController = globalNavController,
                                    onFavoriteClick = { vm.toggleFavoriteAd(ad.id) },
                                    isAuthorized = appState.isLoggedIn
                                )
                            }
                        }
                    }

                    SearchBar(
                        search = searchText,
                        onSearchChange = {
                            searchText = it
                            vm.search(searchText)
                        },
                        showShadow = showShadow
                    )
                }
            }
        }
    }
}