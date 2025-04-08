package samaryanin.avitofork.core.utils.components.utils.space

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Space(value: Dp = 8.dp) {
    Spacer(modifier = Modifier.padding(value))
}
