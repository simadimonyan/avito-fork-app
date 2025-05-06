package samaryanin.avitofork.shared.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import samaryanin.avitofork.app.activity.data.MainViewModel

@Composable
fun RemoteImage(
    imageId: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    viewModel: MainViewModel = hiltViewModel(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(imageId) {
        scope.launch {
            imageBytes = viewModel.loadImage(imageId)
        }
    }

    val imageBitmap = remember(imageBytes) {
        imageBytes?.let {
            runCatching {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.asImageBitmap()
            }.getOrNull()
        }
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier.clip(shape)
        )
    } else {
        ShimmerPlaceholder(modifier = modifier, shape = shape)
    }
}