package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.chat.Chat
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.chat.ChatFontSize
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.chat.ChatIconSize

object AdaptiveLayout : Adaptive {

    data class Compact(
        override val Chat: Chat = Chat(
            IconSize = ChatIconSize(
                iconSizeTopBar1 = 12.dp,
                iconSizeTopBar2 = 25.dp,
                iconSizeTopBar3 = 30.dp,
                iconSizeTopBar4 = 35.dp,
                iconSizeTopBar5 = 50.dp,

                iconSizeAttachment = 30.dp,
            ),
            FontSize = ChatFontSize(
                fontSizeTitle1 = 15.sp,
                fontSizeTitle2 = 20.sp,
                fontSizeTitle3 = 22.sp,

                fontSizeAttachment1 = 15.sp,
                fontSizeAttachment2 = 19.sp,
                fontSizeAttachment3 = 20.sp,
                fontSizeAttachment4 = 22.sp
            ),
        )
    ) : Dimensions

    data class Medium(
        override val Chat: Chat = Chat(
            IconSize = ChatIconSize(
                iconSizeTopBar1 = 12.dp,
                iconSizeTopBar2 = 30.dp,
                iconSizeTopBar3 = 50.dp,
                iconSizeAttachment = 30.dp,
                iconSizeTopBar4 = 50.dp,
                iconSizeTopBar5 = 35.dp
            ),
            FontSize = ChatFontSize(
                fontSizeTitle1 = 15.sp,
                fontSizeTitle2 = 20.sp,
                fontSizeTitle3 = 22.sp,
                fontSizeAttachment1 = 19.sp,
                fontSizeAttachment2 = 20.sp,
                fontSizeAttachment3 = 22.sp,
                fontSizeAttachment4 = 22.sp
            )
        )
    ) : Dimensions

    data class Expanded(
        override val Chat: Chat = Chat(
            IconSize = ChatIconSize(
                iconSizeTopBar1 = 12.dp,
                iconSizeTopBar2 = 30.dp,
                iconSizeTopBar3 = 50.dp,
                iconSizeAttachment = 30.dp,
                iconSizeTopBar4 = 50.dp,
                iconSizeTopBar5 = 35.dp
            ),
            FontSize = ChatFontSize(
                fontSizeTitle1 = 15.sp,
                fontSizeTitle2 = 20.sp,
                fontSizeTitle3 = 22.sp,
                fontSizeAttachment1 = 19.sp,
                fontSizeAttachment2 = 20.sp,
                fontSizeAttachment3 = 22.sp,
                fontSizeAttachment4 = 22.sp
            )
        )
    ) : Dimensions

    override val compact = Compact()
    override val medium = Medium()
    override val expanded = Expanded()

    override fun getThemeSize(screenWidth: Dp): Dimensions {
        return if (screenWidth < 360.dp) compact
        else if (screenWidth > 360.dp && screenWidth < 400.dp) medium
        else expanded
    }

}

