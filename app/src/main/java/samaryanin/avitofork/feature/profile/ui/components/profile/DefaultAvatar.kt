package samaryanin.avitofork.feature.profile.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.theme.navigationSelected
import samaryanin.avitofork.shared.ui.theme.veryLightBlueColor

@Composable
fun DefaultAvatar(name: String, modifier: Modifier = Modifier) {

    val pastelDarkColors = listOf(
        Color(0xFFEC8797),
        Color(0xFFF3BD4F),
        Color(0xFFF3946D),
        Color(0xFF87CEFA),
        Color(0xFF5EB85E),
    ).random()

    val firstLetter = name.firstOrNull()?.uppercase() ?: "?"

    Box(
        modifier = modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(pastelDarkColors),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun AddProfile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(veryLightBlueColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+",
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
            color = navigationSelected
        )
    }
}