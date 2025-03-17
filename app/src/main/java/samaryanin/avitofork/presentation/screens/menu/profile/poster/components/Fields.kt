package samaryanin.avitofork.presentation.screens.menu.profile.poster.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import samaryanin.avitofork.domain.model.post.CategoryField
import samaryanin.avitofork.presentation.ui.components.utils.space.Divider
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.theme.greyButton
import samaryanin.avitofork.presentation.ui.theme.veryLightGray

@Preview
@Composable
fun FieldsPreview() {
    Column {
        MetaTag(
            key = "",
            fields = mutableListOf(
                CategoryField.PhotoPickerField("", 8),
                CategoryField.TextField("Описание:", ""),
                CategoryField.TextField("Описание 1:", ""),
            )
        )
        MetaTag(
            key = "Характеристики 2",
            fields = mutableListOf(
                CategoryField.NumberField("Год выпуска:", "", "г"),
                CategoryField.NumberField("Объем двигателя:", "", "л")
            )
        )
        MetaTag(
            key = "Характеристики 3",
            fields = mutableListOf(
                CategoryField.DropdownField("Тип недвижимости:", "Не выбран", mutableListOf(), true),
                CategoryField.LocationField("Местоположение строения")
            )
        )
    }
}

@Composable
fun MetaTag(key: String, fields: List<CategoryField>) {
    Box(modifier = Modifier.background(veryLightGray)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (key.isNotEmpty()) Text(modifier = Modifier.padding(10.dp), text = key, fontSize = 15.sp, color = Color.Gray, fontWeight = FontWeight.Medium)

            fields.forEach { field ->

                val index = fields.indexOf(field)
                if (index != 0) {
                    Divider()
                }

                when (field) {
                    is CategoryField.TextField -> TextField(field.key, field.value, "до 3000 символов") {}
                    is CategoryField.DropdownField -> DropdownField(field.key, field.value, field.options, field.isOnlyOneToChoose) {}
                    is CategoryField.LocationField -> LocationField(field.key)
                    is CategoryField.NumberField -> NumberField(field.key, field.value, field.unitMeasure, field.value) {}
                    is CategoryField.PhotoPickerByCategoryField -> TODO()
                    is CategoryField.PhotoPickerField -> PhotoPickerField(field.key, field.count)
                    is CategoryField.MetaTag -> MetaTag(key = field.key, field.fields)
                    else -> {}
                }

            }

        }
    }
}

@Composable
fun LocationField(key: String) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.DarkGray,
                modifier = Modifier.size(35.dp)
            )

            Space(7.dp)

            Column {

                Text(modifier = Modifier
                    .padding(end = 16.dp)
                    .width(250.dp), text = key, fontSize = 16.sp, color = Color.Black)

                Space(3.dp)

                Text(modifier = Modifier
                    .width(250.dp), text = "Определяем местоположение", fontSize = 14.sp, color = Color.Gray)

            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Dropdown arrow",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

        }

    }
}

@Composable
fun PhotoPickerField(key: String, count: Int) {

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

@Composable
fun DropdownField(key: String, value: String, options: List<String>, isOnlyOneToChoose: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(modifier = Modifier
                .padding(end = 16.dp)
                .width(200.dp), text = key, fontSize = 15.sp, color = Color.Black)

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier
                    .wrapContentWidth(), text = value, fontSize = 15.sp, color = Color.Gray)

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Dropdown arrow",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(key: String, value: String, unitMeasure: String, placeholder: String, onValueChanged: () -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(modifier = Modifier
                .padding(end = 16.dp)
                .width(200.dp), text = key, fontSize = 15.sp, color = Color.Black)

            Spacer(modifier = Modifier.weight(1f))

            BasicTextField(
                value = value,
                onValueChange = { onValueChanged() },
                Modifier,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = true,
                        placeholder = @Composable {
                            Text(text = placeholder, fontSize = 15.sp, color = Color.Gray)
                        },
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(0.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                        )
                    )
                }
            )

            Text(modifier = Modifier
                .wrapContentWidth(), text = unitMeasure, fontSize = 15.sp, color = Color.Black)

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(key: String, value: String, placeholder: String, onValueChanged: () -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(modifier = Modifier
                .padding(end = 16.dp)
                .width(90.dp), text = key, fontSize = 15.sp, color = Color.Black)

            Spacer(modifier = Modifier.weight(1f))

            BasicTextField(
                value = value,
                onValueChange = { onValueChanged() },
                Modifier.fillMaxWidth(),
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = true,
                        placeholder = @Composable {
                            Text(text = placeholder, fontSize = 15.sp, color = Color.Gray)
                        },
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(0.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                        )
                    )
                }
            )
        }

    }
}