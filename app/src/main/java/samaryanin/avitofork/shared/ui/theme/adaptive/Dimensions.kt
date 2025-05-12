package samaryanin.avitofork.shared.ui.theme.adaptive

import androidx.compose.ui.unit.Dp
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.Chat
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.Messages

interface Adaptive {
    val compact: Dimensions
    val medium: Dimensions
    val expanded: Dimensions

    fun getThemeSize(screenWidth: Dp): Dimensions
}

interface Dimensions {

    // сообщения
    val Chat: Chat
    val Messages: Messages

}

