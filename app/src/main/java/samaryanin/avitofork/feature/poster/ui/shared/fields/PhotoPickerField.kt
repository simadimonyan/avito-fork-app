package samaryanin.avitofork.feature.poster.ui.shared.fields

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.shared.ui.theme.greyButton

@Composable
fun PhotoPickerField(
    draftOptionsObserver: (PostData) -> Unit,
    key: String,
    count: Int,
    uploadPhoto: (Int, Uri) -> Unit
) {

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            var selectedIndex by remember { mutableStateOf<Int?>(null) }
            val imageUris = remember { mutableStateListOf(*Array<Uri?>(count) { null }) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                selectedIndex?.let { index ->
                    if (uri != null) {
                        imageUris[index] = uri
                        uploadPhoto(index, uri)
                    }
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count) { index ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(greyButton)
                            .clickable {
                                selectedIndex = index
                                launcher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUris[index] == null) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Add Photo",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(40.dp)
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(imageUris[index]),
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

        }

    }

}