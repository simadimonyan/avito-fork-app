package samaryanin.avitofork.shared.ui.theme.adaptive.chat

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class Messages(
    val IconSize: MessagesIconSize,
    val FontSize: MessagesFontSize
)

data class MessagesFontSize(
    val fontSizeChip: TextUnit,

    val fontSizeProfileName: TextUnit,
    val fontSizeLastMessage: TextUnit,
    val fontSizeAdName: TextUnit,
    val fontSizeTimestamp: TextUnit,
    val fontSizePrice: TextUnit
)

data class MessagesIconSize(
    val iconSizeSearch: Dp,
    val iconSizePlaceholder: Dp,

    val iconSizeChipHeight: Dp,

    val iconSizeChatAvatar: Dp,
)
