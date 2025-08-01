package samaryanin.avitofork.feature.feed.ui.feature.card.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.components.utils.space.Space

@Composable
fun SelectableLazyRow() {
    val items = listOf("Рекомендации", "Свежие", "Рядом")
    var selectedItem by remember { mutableStateOf(items.first()) }

    Space()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->
            Text(
                text = item,
                softWrap = true,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                style = MaterialTheme.typography.titleLarge,
                color = if (item == selectedItem) Color.Black else Color.Gray,
                modifier = Modifier
                    .clickable { selectedItem = item }
            )
        }
    }

    Space()

}