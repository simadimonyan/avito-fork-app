package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.marketplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(iconRes: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = onClick)
            .size(24.dp)
            .padding(end = 8.dp)
    )
}