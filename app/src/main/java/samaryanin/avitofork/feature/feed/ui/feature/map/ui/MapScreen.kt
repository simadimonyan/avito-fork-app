package samaryanin.avitofork.feature.feed.ui.feature.map.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.feed.ui.feature.map.domain.model.MapsView

@Composable
fun MapScreen(globalNavController: NavHostController) {
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

    val listPlaceMark = listOf(
        Point(55.751244, 37.618423), // Москва
        Point(59.937500, 30.308611), // Санкт-Петербург
        Point(56.851900, 60.612200)  // Екатеринбург
    )

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            MapsView(ctx).apply {

                val imageProvider = ImageProvider.fromResource(ctx, R.drawable.pin)

                val pinsCollection = mapWindow.map.mapObjects.addCollection()

                listPlaceMark.forEach { itemMark ->
                    val place = pinsCollection.addPlacemark().apply {
                        geometry = itemMark
                        setIcon(imageProvider)
                    }
                    val tapListener = MapObjectTapListener { _, _ ->
                        Toast.makeText(
                            ctx,
                            "Широта :${itemMark.latitude}, Долгота: ${itemMark.longitude}",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    listTaps.add(tapListener)
                    place.addTapListener(tapListener)
                }

                this.mapWindow.map.move(
                    CameraPosition(
                        Point(59.936046, 30.326869), 15.0f, 0f, 0f
                    )
                )
            }
        }
    )
}
