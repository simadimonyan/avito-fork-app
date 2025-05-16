package samaryanin.avitofork.feature.feed.ui.feature.map.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.feed.ui.feature.map.domain.model.MapsView

@Composable
fun MapScreen(lat: Double, lon: Double) {
    val context = LocalContext.current

    LifeScreen(
        onStart = {
            MapKitFactory.initialize(context)
            MapKitFactory.getInstance().onStart()
        },
        onStop = {
            MapKitFactory.getInstance().onStop()
        },
    )

    val listTaps: MutableList<MapObjectTapListener> = remember { mutableListOf() }

    val point = Point(lat, lon)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            MapsView(ctx).apply {
                val imageProvider = ImageProvider.fromResource(ctx, R.drawable.pin)
                val pinsCollection = mapWindow.map.mapObjects.addCollection()

                val place = pinsCollection.addPlacemark().apply {
                    geometry = point
                    setIcon(imageProvider)
                }
                val tapListener = MapObjectTapListener { _, _ ->
                    Toast.makeText(
                        ctx,
                        "Широта :${point.latitude}, Долгота: ${point.longitude}",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                listTaps.add(tapListener)
                place.addTapListener(tapListener)

                mapWindow.map.move(
                    CameraPosition(point, 15.0f, 0f, 0f)
                )
            }
        }
    )
}