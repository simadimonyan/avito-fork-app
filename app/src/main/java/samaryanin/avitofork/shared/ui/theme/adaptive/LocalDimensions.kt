package samaryanin.avitofork.shared.ui.theme.adaptive

import androidx.compose.runtime.compositionLocalOf

val layout = AdaptiveLayout()
val LocaleDimensions = compositionLocalOf<Dimensions> { layout.medium }