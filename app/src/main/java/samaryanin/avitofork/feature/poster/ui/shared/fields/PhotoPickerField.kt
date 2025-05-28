package samaryanin.avitofork.feature.poster.ui.shared.fields

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.rememberAsyncImagePainter
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.shared.ui.theme.greyButton

@Composable
fun PhotoPickerField(
    draftOptionsObserver: (PostData) -> Unit,
    key: String,
    photos: MutableMap<Int, String>,
    count: Int,
    uploadPhoto: (Int, Uri, (Boolean) -> Unit) -> Unit,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit
) {
    var showUploadErrorDialog by remember { mutableStateOf(false) }
    var showUploadProgressDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            var selectedIndex by remember { mutableStateOf<Int?>(null) }
            val imageUris = remember { mutableStateListOf(*Array<Uri?>(count) { null }) }
            var isError by rememberSaveable { mutableStateOf(false) }

            // загрузка фотографий если они есть в кеше черновика
            LaunchedEffect(Unit) {
                if (photos.isNotEmpty()) {
                    photos.onEachIndexed { index, uri ->
                        if (uri != null) { //TODO (подгружать картинку по id с сервера)
//                            uploadPhoto(index, uri) { success ->
//                                showUploadProgressDialog = false
//                                if (success) {
//                                    imageUris[index] = uri
//                                } else {
//                                    showUploadErrorDialog = true
//                                }
//                            }
                        }
                    }
                }
            }

            // обработка ошибок загрузки фотографий
            LaunchedEffect(isRequiredCheckSubmitted) {
                if (isRequiredCheckSubmitted == true) {
                    isError = imageUris.all { it == null }
                    if (isError) {
                        showErrorMessage("Фотографии")
                    }
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                selectedIndex?.let { index ->
                    if (uri != null) {
                        isError = false
                        showUploadProgressDialog = true
                        uploadPhoto(index, uri) { success ->
                            showUploadProgressDialog = false
                            if (success) {
                                imageUris[index] = uri
                            } else {
                                showUploadErrorDialog = true
                            }
                        }
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
                            .then(
                                if (isError) Modifier.border(
                                    2.dp,
                                    Color.Red,
                                    RoundedCornerShape(12.dp)
                                )
                                else Modifier
                            )
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

        // загрузка фотографии на сервер
        if (showUploadProgressDialog) {
            Dialog(
                onDismissRequest = {}
            ) {
                //(LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0f)

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp).background(Color.White).padding(13.dp),
                        color = Color.LightGray,
                        trackColor = Color.Black,
                    )
                }
            }
//            AlertDialog(
//                onDismissRequest = {},
//                confirmButton = {},
//                title = { Text("Загрузка") },
//                text = { Text("Фотография загружается на сервер...") }
//            )
        }

        // ошибка загрузки фотографии
        if (showUploadErrorDialog) {
            AlertDialog(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(0.dp),
                onDismissRequest = { showUploadErrorDialog = false },
                confirmButton = {
                    Button(
                        onClick = { showUploadErrorDialog = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Ок")
                    }
                },
                title = {
                    Text("Ошибка загрузки", fontSize = 17.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                },
                text = {
                    Text("Не удалось загрузить фото", fontSize = 14.sp, color = Color.Black)
                },
                containerColor = Color.White
            )
        }

    }

}