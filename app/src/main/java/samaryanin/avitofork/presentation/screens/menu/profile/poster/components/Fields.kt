package samaryanin.avitofork.presentation.screens.menu.profile.poster.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryField
import samaryanin.avitofork.presentation.ui.components.utils.space.Divider
import samaryanin.avitofork.presentation.ui.theme.veryLightGray

@Preview
@Composable
fun FieldsPreview() {
    Column {
        MetaTag(
            key = "Характеристики",
            fields = mutableSetOf(
                CategoryField.TextField("Описание:", ""),
                CategoryField.TextField("Описание 1:", ""),
                CategoryField.TextField("Описание 2:", "")
            )
        )
        MetaTag(
            key = "Характеристики 2",
            fields = mutableSetOf(
                CategoryField.TextField("Описание:", ""),
                CategoryField.TextField("Описание 1:", ""),
                CategoryField.TextField("Описание 2:", "")
            )
        )
    }
}

@Composable
fun MetaTag(key: String, fields: Set<CategoryField>) {
    Box(modifier = Modifier.background(veryLightGray)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier.padding(10.dp), text = key, fontSize = 15.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                items(fields.toList(), key = { it.hashCode() }) { field ->

                    val index = fields.indexOf(field)
                    if (index != 0) {
                        Divider()
                    }

                    when (field) {
                        is CategoryField.TextField -> TextField(field.key, field.value, "до 3000 символов") {}
                        is CategoryField.DropdownField -> TODO()
                        is CategoryField.LocationField -> TODO()
                        is CategoryField.MetaTag -> TODO()
                        is CategoryField.NumberField -> TODO()
                        is CategoryField.PhotoPickerByCategoryField -> TODO()
                        is CategoryField.PhotoPickerField -> TODO()
                    }
                }

            }
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

            Text(modifier = Modifier.padding(end = 16.dp).width(90.dp), text = key, fontSize = 15.sp, color = Color.Black)

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