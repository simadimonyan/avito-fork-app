package samaryanin.avitofork.feature.messages.ui.feature.messages.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.components.utils.space.Space

@Preview(showBackground = true)
@Composable
fun FilterItemListPreview() {
    FilterItemList()
}

@Composable
fun FilterItemList() {

    Row {

        Space()

        SuggestionChip(
            onClick = {
                //TODO()
            },
            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
            label = {
                Text("Все чаты", color = Color.Black, fontSize = 17.sp)
            },
            border = BorderStroke(1.dp, Color.Black)
        )

        Space()

        SuggestionChip(
            onClick = {
                //TODO()
            },
            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
            label = {
                Text("Ваши отклики", color = Color.Black, fontSize = 17.sp)
            },
            border = BorderStroke(1.dp, Color.Black)
        )

        Space()

    }

}