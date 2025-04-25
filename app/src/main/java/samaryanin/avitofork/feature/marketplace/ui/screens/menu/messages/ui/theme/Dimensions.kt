package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.Dimensions.Compact
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.Dimensions.Expanded
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.theme.Dimensions.Medium

interface IDimensions {

    // icons
    val smallIcon1: Dp

    val mediumIcon1: Dp
    val mediumIcon2: Dp
    val mediumIcon3: Dp

    val largeIcon1: Dp

    // font
    val smallFont1: TextUnit

    val mediumFont1: TextUnit
    val mediumFont2: TextUnit
    val mediumFont3: TextUnit

    // paddings
    val smallPadding1: Dp
    val smallPadding2: Dp
    val smallPadding3: Dp
    val smallPadding4: Dp
    val smallPadding5: Dp

    val mediumPadding1: Dp
    val mediumPadding2: Dp

}

fun getParams(density: Dp): Dimensions {
    return if (density < 360.dp) Compact()
    else if (density > 360.dp && density < 400.dp) Medium()
    else Expanded()
}

sealed class Dimensions : IDimensions {

    data class Compact(

        // icons
        override val smallIcon1: Dp = 12.dp,

        override val mediumIcon1: Dp = 30.dp,
        override val mediumIcon2: Dp = 25.dp,
        override val mediumIcon3: Dp = 35.dp,

        override val largeIcon1: Dp = 50.dp,

        // font
        override val smallFont1: TextUnit = 15.sp,

        override val mediumFont1: TextUnit = 22.sp,
        override val mediumFont2: TextUnit = 20.sp,
        override val mediumFont3: TextUnit = 19.sp,

        // paddings
        override val smallPadding1: Dp = 10.dp,
        override val smallPadding2: Dp = 6.dp,
        override val smallPadding3: Dp = 5.dp,
        override val smallPadding4: Dp = 1.dp,
        override val smallPadding5: Dp = 2.dp,

        override val mediumPadding1: Dp = 15.dp,
        override val mediumPadding2: Dp = 17.dp

    ) : Dimensions()

    data class Medium(

        // icons
        override val smallIcon1: Dp = 12.dp,

        override val mediumIcon1: Dp = 30.dp,
        override val mediumIcon2: Dp = 25.dp,
        override val mediumIcon3: Dp = 35.dp,

        override val largeIcon1: Dp = 50.dp,

        // font
        override val smallFont1: TextUnit = 15.sp,

        override val mediumFont1: TextUnit = 22.sp,
        override val mediumFont2: TextUnit = 20.sp,
        override val mediumFont3: TextUnit = 19.sp,

        // paddings
        override val smallPadding1: Dp = 10.dp,
        override val smallPadding2: Dp = 6.dp,
        override val smallPadding3: Dp = 5.dp,
        override val smallPadding4: Dp = 1.dp,
        override val smallPadding5: Dp = 2.dp,

        override val mediumPadding1: Dp = 15.dp,
        override val mediumPadding2: Dp = 17.dp

    ) : Dimensions()

    data class Expanded(

        // icons
        override val smallIcon1: Dp = 12.dp,

        override val mediumIcon1: Dp = 30.dp,
        override val mediumIcon2: Dp = 25.dp,
        override val mediumIcon3: Dp = 35.dp,

        override val largeIcon1: Dp = 50.dp,

        // font
        override val smallFont1: TextUnit = 15.sp,

        override val mediumFont1: TextUnit = 22.sp,
        override val mediumFont2: TextUnit = 20.sp,
        override val mediumFont3: TextUnit = 19.sp,

        // paddings
        override val smallPadding1: Dp = 10.dp,
        override val smallPadding2: Dp = 6.dp,
        override val smallPadding3: Dp = 5.dp,
        override val smallPadding4: Dp = 1.dp,
        override val smallPadding5: Dp = 2.dp,

        override val mediumPadding1: Dp = 15.dp,
        override val mediumPadding2: Dp = 17.dp

    ) : Dimensions()

}

