package samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.marketplace

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.core.utils.components.utils.space.Space
import samaryanin.avitofork.core.utils.theme.AvitoForkTheme
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.MarketplaceViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.additional_categories.CategoriesWithPhotos
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.main_categories.SelectableLazyRow
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.product.ProductCard

@Composable
fun MarketplaceScreen(globalNavController: NavHostController) {

    var search by remember { mutableStateOf("") }
    val viewModel: MarketplaceViewModel = hiltViewModel()
    val ads by viewModel.allAds.collectAsState()
    val categories by viewModel.allCategories.collectAsState()
    val selectedCategoryIds by viewModel.selectedCategoryIds.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    val lazyGridState = rememberLazyGridState()

    val showShadow by remember {
        derivedStateOf { lazyGridState.firstVisibleItemIndex > 0 }
    }

    AvitoForkTheme {

        Scaffold(
            containerColor = Color.White,
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

                    items(ads.orEmpty()) { ad ->
                        ProductCard(
                            ad = ad,
                            isFav = favoriteIds.contains(ad.id),
                            globalNavController = globalNavController,
                            onFavoriteClick = {
                               viewModel.toggleFavoriteAd(ad.id)
                            }
                        )
                    }
                }
                SearchBar(search = search, onSearchChange = { search = it }, showShadow)
            }
        }
    }
}