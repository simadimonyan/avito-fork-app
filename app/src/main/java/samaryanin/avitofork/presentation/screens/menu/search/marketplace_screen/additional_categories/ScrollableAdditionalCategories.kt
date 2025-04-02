package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.additional_categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import samaryanin.avitofork.R

@Composable
fun ScrollableAdditionalCategories() {
    val categories = listOf(
        Category("Авто", R.drawable.car_black),
        Category("Недвижимость", R.drawable.service_black),
        Category("Услуги", R.drawable.service_black),
        Category("Электроника", R.drawable.smartphone_black)
    )

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(categories) { category ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { /* Handle click */ },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = category.imageRes,
                    contentDescription = category.name,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Fit
                )
                Text(text = category.name)
            }
        }
    }
}