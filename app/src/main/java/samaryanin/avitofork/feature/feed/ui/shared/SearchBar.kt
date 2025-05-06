package samaryanin.avitofork.feature.feed.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.components.utils.field.AppTextFieldPlaceholder

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(search = "", onSearchChange = {}, showShadow = true)
}

@Composable
fun SearchBar(search: String, onSearchChange: (String) -> Unit, showShadow: Boolean) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 1.dp)
            .then(
                Modifier.shadow(if (showShadow) 2.dp else 0.dp, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            ),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 17.dp, vertical = 12.dp)
        ) {

            IconButton(R.drawable.search) {
                //click
            }

            Space()

            AppTextFieldPlaceholder(
                value = search,
                onValueChange = onSearchChange,
                placeholder = "Поиск",
                errorListener = false,
                modifier = Modifier.weight(1f)
            )

            Space()

            IconButton(R.drawable.filter) {
                //click
            }

        }

    }

}