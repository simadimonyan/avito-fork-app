package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme

import androidx.compose.ui.unit.Dp
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.chat.Chat

interface Adaptive {
    val compact: Dimensions
    val medium: Dimensions
    val expanded: Dimensions

    fun getThemeSize(screenWidth: Dp): Dimensions
}

interface Dimensions {
    val Chat: Chat
}

