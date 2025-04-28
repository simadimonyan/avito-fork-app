package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.chat

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class Chat(
    val IconSize: ChatIconSize,
    val FontSize: ChatFontSize
)

data class ChatFontSize(
    val fontSizeTitle1: TextUnit,
    val fontSizeTitle2: TextUnit,
    val fontSizeTitle3: TextUnit,

    val fontSizeAttachment1: TextUnit,
    val fontSizeAttachment2: TextUnit,
    val fontSizeAttachment3: TextUnit,
    val fontSizeAttachment4: TextUnit
)

data class ChatIconSize(
    val iconSizeTopBar1: Dp,
    val iconSizeTopBar2: Dp,
    val iconSizeTopBar3: Dp,
    val iconSizeTopBar4: Dp,
    val iconSizeTopBar5: Dp,

    val iconSizeAttachment: Dp,
)

