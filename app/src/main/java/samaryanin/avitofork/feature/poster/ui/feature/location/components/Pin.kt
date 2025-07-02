package samaryanin.avitofork.feature.poster.ui.feature.location.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R

@Composable
fun Pin(
    dragging: Boolean,
    modifier: Modifier = Modifier
) {
    val shadowScale by animateFloatAsState(targetValue = if (dragging) 0.75f else 1f)
    val draggingTransition by animateIntOffsetAsState(
        if (dragging) IntOffset(0, 0) else IntOffset(0, 23)
    )

    Box {
        Box(
            modifier = modifier
                .size(60.dp)
                .graphicsLayer {
                    scaleX = shadowScale
                    scaleY = shadowScale
                    shadowElevation = 0f
                    shape = CircleShape
                    clip = false
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp).offset(0.dp, 27.dp)
                    .background(Color.Black.copy(alpha = 0.25f), shape = CircleShape).align(Alignment.Center)
                    .blur(8.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.location),
            contentDescription = "pin",
            tint = Color.Red,
            modifier = Modifier
                .size(40.dp)
                .offset { IntOffset(0, draggingTransition.y) }
                .align(Alignment.Center)
        )
    }
}