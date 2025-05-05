package samaryanin.avitofork.shared.ui.theme.adaptive.chat

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

    val fontSizeAttachmentPost: TextUnit,
    val fontSizeAttachmentPrice: TextUnit,

    val fontSizeMessage1: TextUnit,
    val fontSizeMessageTimestamp: TextUnit
)

data class ChatIconSize(
    val iconSizeTopBar1: Dp,
    val iconSizeTopBar2: Dp,
    val iconSizeTopBar3: Dp,
    val iconSizeTopBar4: Dp,

    val iconSizeAttachment: Dp,
    val iconSizeBottomBar1: Dp,
)

