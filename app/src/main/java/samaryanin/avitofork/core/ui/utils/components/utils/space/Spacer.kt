package samaryanin.avitofork.core.ui.utils.components.utils.space

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Space(value: Dp = 8.dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.padding(value))
}
