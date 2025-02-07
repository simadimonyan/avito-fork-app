package samaryanin.avitofork.presentation.screens.poster

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder

@Preview(showSystemUi = false)
@Composable
fun MarketplaceScreenPreview() {
    MarketplaceScreen()
}

@Composable
fun MarketplaceScreen() {
    var search by remember { mutableStateOf("") }

    val ads = listOf(
        Product("Laptop", "$1200", "New York", "https://via.placeholder.com/150"),
        Product("Phone", "$800", "Los Angeles", "https://via.placeholder.com/150"),
        Product("Bike", "$400", "Chicago", "https://via.placeholder.com/150"),
        Product("Camera", "$600", "Miami", "https://via.placeholder.com/150"),
    )

    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(8.dp)){
                AppTextFieldPlaceholder(placeholder = "Поиск", value = search,
                    onValueChange = { search = it }, isPassword = false, errorListener = false
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(ads.size) { index ->
                    ProductCard(ads[index])
                }
            }
        }

    }
}