package samaryanin.avitofork.feature.feed.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(iconRes: Int, onClick: () -> Unit, size: Dp = 24.dp) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = onClick)
            .size(size)
            .padding(2.dp)
    )
}