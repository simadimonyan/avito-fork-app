package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.data.database.favorites.Ad
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.additional_categories.CategoriesWithPhotos
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.additional_categories.ScrollableAdditionalCategories
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.main_categories.SelectableLazyRow
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.product.ProductCard
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder
import samaryanin.avitofork.presentation.ui.theme.AvitoForkTheme

@Composable
fun MarketplaceScreen(globalNavController: NavHostController) {
    var search by remember { mutableStateOf("") }
    val viewModel: MarketplaceViewModel = hiltViewModel()
    val ads by viewModel.allAds.collectAsState()
    val favoriteAds by viewModel.favoriteAds.collectAsState()

    val adsList = listOf(
        Ad(1, "Квартира в центре", "50 000 ₽", "Москва, ул. Ленина",
            "https://example.com/image1.jpg"),
        Ad(2, "Дом у озера", "80 000 ₽", "Подмосковье", ""),
        Ad(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        Ad(4, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        Ad(5, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        Ad(6, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        Ad(7, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        Ad(8, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
    )
    viewModel.addAds(adsList)
    AvitoForkTheme {
        Scaffold { paddingValues ->
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(search) { search = it }
                    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 150.dp),
                            modifier = Modifier.padding(bottom = 50.dp),
                            contentPadding = PaddingValues(bottom = 50.dp)
                        ) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                ScrollableAdditionalCategories()
                            }
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                CategoriesWithPhotos()
                            }
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                SelectableLazyRow()
                            }
                            items(ads) { ad ->
                                val isFav = favoriteAds.any { it.id == ad.id }
                                ProductCard(ad, isFav, globalNavController) { viewModel.toggleFavorite(ad) }
                            }
                        }
                    }
            }
        }
    }
}

@Composable
fun SearchBar(search: String, onSearchChange: (String) -> Unit) {
    Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.CenterEnd) {
        AppTextFieldPlaceholder(
            placeholder = "Поиск",
            value = search,
            onValueChange = onSearchChange,
            isPassword = false,
            errorListener = false
        )

        Row(modifier = Modifier.padding(end = 8.dp)) {
            IconButton(R.drawable.search) {
                //click
            }
            IconButton(R.drawable.filter) {
                //click
            }
        }
    }
}

@Composable
fun IconButton(iconRes: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = onClick)
            .size(24.dp)
            .padding(end = 8.dp)
    )
}