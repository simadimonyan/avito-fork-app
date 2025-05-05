package samaryanin.avitofork.shared.ui.theme.adaptive

import androidx.compose.ui.unit.Dp
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.Chat

interface Adaptive {
    val compact: Dimensions
    val medium: Dimensions
    val expanded: Dimensions

    fun getThemeSize(screenWidth: Dp): Dimensions
}

interface Dimensions {
    val Chat: Chat
}

