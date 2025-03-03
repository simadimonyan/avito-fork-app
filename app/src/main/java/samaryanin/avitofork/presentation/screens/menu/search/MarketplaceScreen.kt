package samaryanin.avitofork.presentation.screens.menu.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder

@Preview(showSystemUi = false)
@Composable
fun MarketplaceScreenPreview() {
    MarketplaceScreen()
}

@Composable
fun MarketplaceScreen() {
    var search by remember { mutableStateOf("") }
    val ads = List(10) { Product("Camera 2000 аmsp", "600$", "Ростов-На-Дону", "url") }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(search) { search = it }
            SelectableLazyRow()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(ads.size) { index -> ProductCard(ads[index]) }
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
