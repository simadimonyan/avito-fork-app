package samaryanin.avitofork.feature.messages.ui.feature.messages.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.adaptive.LocaleDimensions

@Preview(showBackground = true)
@Composable
fun FilterItemListPreview() {
    FilterItemList()
}

@Composable
fun FilterItemList() {

    val LocalDimensions = LocaleDimensions.current

    Row {

        Space()

        SuggestionChip(
            onClick = {
                //TODO()
            },
            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
            label = {
                Text(
                    "Все чаты",
                    color = Color.Black,
                    fontSize = LocalDimensions.Messages.FontSize.fontSizeChip
                )
            },
            modifier = Modifier.height(LocalDimensions.Messages.IconSize.iconSizeChipHeight),
            border = BorderStroke(1.dp, Color.Black)
        )

        Space()

        SuggestionChip(
            onClick = {
                //TODO()
            },
            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
            label = {
                Text(
                    "Ваши отклики",
                    color = Color.Black,
                    fontSize = LocalDimensions.Messages.FontSize.fontSizeChip
                )
            },
            modifier = Modifier.height(LocalDimensions.Messages.IconSize.iconSizeChipHeight),
            border = BorderStroke(1.dp, Color.Black)
        )

        Space()

    }

}