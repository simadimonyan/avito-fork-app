package samaryanin.avitofork.feature.poster.ui.feature.location.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.feed.ui.shared.IconButton
import samaryanin.avitofork.shared.ui.components.utils.field.AppTextFieldPlaceholder
import samaryanin.avitofork.shared.ui.components.utils.space.Space

@Composable
fun LocationSearchBar(
    onExit: () -> Unit,
    search: String,
    onSearchChange: (String) -> Unit,
    onFocusEvent: (FocusState) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {

        val focusManager = LocalFocusManager.current
        var focused by remember { mutableStateOf(false) }

        IconButton(
            R.drawable.ic_arrow,
            modifier = Modifier,
            onClick = {
                onExit()
            },
            size = 40.dp
        )

        Space(3.dp)

        AppTextFieldPlaceholder(
            value = search,
            onValueChange = onSearchChange,
            placeholder = "Поиск по месту",
            errorListener = false,
            modifier = Modifier
                .weight(1f)
                .size(60.dp)
                .onFocusEvent { state ->
                    focused = state.hasFocus
                    onFocusEvent(state)
                },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { if (search.isNotEmpty()) {
                    onSearchChange(search)
                } }
            )
        )

        Space()

        if (focused) {
            IconButton(
                R.drawable.ic_close,
                modifier = Modifier,
                onClick = {
                    onSearchChange("")
                    focusManager.clearFocus()
                },
                size = 27.dp
            )
        }
        else {
            IconButton(
                R.drawable.search,
                modifier = Modifier,
                onClick = {},
                size = 27.dp
            )
        }

        Space(5.dp)

    }

}