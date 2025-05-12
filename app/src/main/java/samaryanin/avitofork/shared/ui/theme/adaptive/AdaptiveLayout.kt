package samaryanin.avitofork.shared.ui.theme.adaptive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.Chat
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.ChatFontSize
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.ChatIconSize
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.Messages
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.MessagesFontSize
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.MessagesIconSize

class AdaptiveLayout : Adaptive {

    data class Compact(

        // сообщения

        override val Chat: Chat = Chat(
            IconSize = ChatIconSize(
                iconSizeTopBar1 = 13.dp,
                iconSizeTopBar2 = 24.dp,
                iconSizeTopBar3 = 37.dp,
                iconSizeTopBar4 = 43.dp,

                iconSizeAttachment = 25.dp,
                iconSizeBottomBar1 = 25.dp
            ),
            FontSize = ChatFontSize(
                fontSizeTitle1 = 14.sp,
                fontSizeTitle2 = 17.sp,
                fontSizeTitle3 = 20.sp,

                fontSizeAttachmentPost = 15.sp,
                fontSizeAttachmentPrice = 16.sp,

                fontSizeMessage1 = 15.sp,
                fontSizeMessageTimestamp = 10.sp,
            )
        ),

        override val Messages: Messages = Messages(
            IconSize = MessagesIconSize(
                iconSizeSearch = 22.dp,
                iconSizePlaceholder = 50.dp,

                iconSizeChipHeight = 32.dp,

                iconSizeChatAvatar = 70.dp
            ),
            FontSize = MessagesFontSize(
                fontSizeChip = 14.sp,
                fontSizeProfileName = 17.sp,
                fontSizeLastMessage = 16.sp,
                fontSizeAdName = 16.sp,
                fontSizeTimestamp = 10.sp,
                fontSizePrice = 16.sp
            )
        ),

    ) : Dimensions

    data class Medium(

        // сообщения

        override val Chat: Chat = Chat(
            IconSize = ChatIconSize(
                iconSizeTopBar1 = 15.dp,
                iconSizeTopBar2 = 26.dp,
                iconSizeTopBar3 = 40.dp,
                iconSizeTopBar4 = 46.dp,

                iconSizeAttachment = 28.dp,
                iconSizeBottomBar1 = 28.dp
            ),
            FontSize = ChatFontSize(
                fontSizeTitle1 = 16.sp,
                fontSizeTitle2 = 19.sp,
                fontSizeTitle3 = 22.sp,

                fontSizeAttachmentPost = 17.sp,
                fontSizeAttachmentPrice = 18.sp,

                fontSizeMessage1 = 17.sp,
                fontSizeMessageTimestamp = 12.sp,
            )
        ),

        override val Messages: Messages = Messages(
            IconSize = MessagesIconSize(
                iconSizeSearch = 24.dp,
                iconSizePlaceholder = 52.dp,

                iconSizeChipHeight = 35.dp,

                iconSizeChatAvatar = 74.dp
            ),
            FontSize = MessagesFontSize(
                fontSizeChip = 17.sp,
                fontSizeProfileName = 20.sp,
                fontSizeLastMessage = 18.sp,
                fontSizeAdName = 18.sp,
                fontSizeTimestamp = 13.sp,
                fontSizePrice = 18.sp
            )
        ),

    ) : Dimensions

    data class Expanded(

        // сообщения

        override val Chat: Chat = Chat(
            IconSize = ChatIconSize(
                iconSizeTopBar1 = 17.dp,
                iconSizeTopBar2 = 28.dp,
                iconSizeTopBar3 = 43.dp,
                iconSizeTopBar4 = 49.dp,

                iconSizeAttachment = 30.dp,
                iconSizeBottomBar1 = 30.dp
            ),
            FontSize = ChatFontSize(
                fontSizeTitle1 = 18.sp,
                fontSizeTitle2 = 21.sp,
                fontSizeTitle3 = 24.sp,

                fontSizeAttachmentPost = 19.sp,
                fontSizeAttachmentPrice = 20.sp,

                fontSizeMessage1 = 19.sp,
                fontSizeMessageTimestamp = 14.sp,
            )
        ),

        override val Messages: Messages = Messages(
            IconSize = MessagesIconSize(
                iconSizeSearch = 26.dp,
                iconSizePlaceholder = 54.dp,

                iconSizeChipHeight = 37.dp,

                iconSizeChatAvatar = 76.dp
            ),
            FontSize = MessagesFontSize(
                fontSizeChip = 20.sp,
                fontSizeProfileName = 22.sp,
                fontSizeLastMessage = 19.sp,
                fontSizeAdName = 19.sp,
                fontSizeTimestamp = 15.sp,
                fontSizePrice = 19.sp
            )
        ),

    ) : Dimensions

    override val compact = Compact()
    override val medium = Medium()
    override val expanded = Expanded()

    override fun getThemeSize(screenWidth: Dp): Dimensions {
        return if (screenWidth <= 600.dp) compact
        else if (screenWidth > 600.dp && screenWidth <= 840.dp) medium
        else expanded
    }

}