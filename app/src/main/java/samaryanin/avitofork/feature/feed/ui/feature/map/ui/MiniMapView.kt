package samaryanin.avitofork.feature.feed.ui.feature.map.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.feed.ui.feature.map.domain.model.MapsView

@Composable
fun MiniMapView(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        AndroidView(
            factory = { ctx ->
                MapsView(ctx).apply {
                    val point = Point(latitude, longitude)
                    val imageProvider = ImageProvider.fromResource(ctx, R.drawable.pin)

                    mapWindow.map.mapObjects.addPlacemark(point, imageProvider)

                    mapWindow.map.move(
                        CameraPosition(point, 14.0f, 0f, 0f)
                    )

                    mapWindow.map.isRotateGesturesEnabled = false
                    mapWindow.map.isScrollGesturesEnabled = false
                    mapWindow.map.isTiltGesturesEnabled = false
                    mapWindow.map.isZoomGesturesEnabled = false
                }
            },
            modifier = Modifier.matchParentSize()
        )

        // Прозрачная кликабельная область поверх карты
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClick = onClick)
        )
    }
}