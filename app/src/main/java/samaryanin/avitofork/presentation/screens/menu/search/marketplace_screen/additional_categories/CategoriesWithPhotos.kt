package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.additional_categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.domain.model.favorites.Category

@Composable
fun CategoriesWithPhotos(
    categories: List<Category>,
    selectedCategoryIds: List<String>,
    onSelectedCategoriesIdsChange: (List<String>) -> Unit,
) {
    val categoriesUI = categories.map { it.toCategoryUI() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categoriesUI) { category ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val isSelected = selectedCategoryIds.contains(category.id)
                    CategoryCard(
                        category = category,
                        isSelected = isSelected,
                        onCategorySelected = {
                            if (isSelected) {
                                onSelectedCategoriesIdsChange(selectedCategoryIds - category.id)
                            } else {
                                onSelectedCategoriesIdsChange(selectedCategoryIds + category.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: CategoryUI,
    isSelected: Boolean,
    onCategorySelected: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(90.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = onCategorySelected,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.Center)
            )
            val bgColor = if (isSelected) {
                Color(0xFF6200EE)
            } else {
                Color.Black
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(bgColor.copy(alpha = 0.5f))
                    .padding(4.dp)
            ) {
                Text(
                    text = category.name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}