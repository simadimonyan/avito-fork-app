package samaryanin.avitofork.shared.ui.components.utils.space

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider() {
    Box(modifier = Modifier.background(Color.LightGray).fillMaxWidth().height(0.2.dp))
}