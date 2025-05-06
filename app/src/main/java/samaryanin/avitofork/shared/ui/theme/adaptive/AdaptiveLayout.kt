package samaryanin.avitofork.shared.ui.theme.adaptive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.Chat
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.ChatFontSize
import samaryanin.avitofork.shared.ui.theme.adaptive.chat.ChatIconSize

class AdaptiveLayout : Adaptive {

    data class Compact(
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
        )
    ) : Dimensions

    data class Medium(
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
        )
    ) : Dimensions

    data class Expanded(
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
        )
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