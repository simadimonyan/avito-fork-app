package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder


@Composable
fun SearchBar(search: String, onSearchChange: (String) -> Unit) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))

        ) {
            Spacer(modifier = Modifier.width(8.dp))
            AppTextFieldPlaceholder(
                value = search,
                onValueChange = onSearchChange,
                placeholder = "Поиск",
                errorListener = false,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(R.drawable.search) {
                //click
            }
            IconButton(R.drawable.filter) {
                //click
            }
        }
}