package samaryanin.avitofork.feature.messages.ui.feature.chat.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.components.utils.field.AppTextFieldPlaceholder
import samaryanin.avitofork.feature.feed.ui.shared.IconButton
import samaryanin.avitofork.shared.ui.theme.adaptive.LocaleDimensions

@Preview
@Composable
fun ChatSearchBarPreview() {
    ChatTopBar(search = "", onSearchChange = {})
}

@Composable
fun ChatTopBar(search: String, onSearchChange: (String) -> Unit) {

    val LocalDimensions = LocaleDimensions.current


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 17.dp, vertical = 0.dp)
    ) {

        IconButton(
            R.drawable.search,
            onClick = {},
            size = LocalDimensions.Messages.IconSize.iconSizeSearch
        )

        Space()

        AppTextFieldPlaceholder(
            value = search,
            onValueChange = onSearchChange,
            placeholder = "Поиск по сообщениям",
            errorListener = false,
            modifier = Modifier.weight(1f)
                .size(LocalDimensions.Messages.IconSize.iconSizePlaceholder)
        )

    }

}