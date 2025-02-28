package samaryanin.avitofork.presentation.screens.menu.search.poster

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectableLazyRow() {
    val items = listOf("Рекомендации", "Свежие", "Рядом", "Тест", "Еще поле")
    var selectedItem by remember { mutableStateOf(items.first()) }

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->
            Text(
                text = item,
                softWrap = true,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = if (item == selectedItem) Color.Black else Color.Gray,
                modifier = Modifier
                    .clickable { selectedItem = item }
            )
        }
    }
}