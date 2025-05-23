package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.text.iterator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(
    // дефолтные значения поля
    key: String,
    value: String,
    unitMeasure: String,
    placeholder: String,

    // характеристики
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isRequired: Boolean,
    isRequiredCheckSubmitted: Boolean,

    // колбек функции
    showErrorMessage: (String) -> Unit = {},
    onValueChanged: (String) -> Unit = {}
) {

    val interactionSource = remember { MutableInteractionSource() }
    var mutableValue by remember { mutableStateOf(value) }
    var mutablePlaceholder by remember { mutableStateOf(placeholder) }
    var isError by remember { mutableStateOf(false) }

    // если проверка поля инициирована – отправить ошибку
    LaunchedEffect(isRequiredCheckSubmitted) {
        if (isRequired && isRequiredCheckSubmitted) {
            val cleaned = mutableValue.trim()
            val valueAsNumber = cleaned.toDoubleOrNull()
            isError = cleaned.isEmpty() || valueAsNumber == null || valueAsNumber == 0.0
            if (isError) showErrorMessage(key)
        }
    }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(
                modifier = Modifier.wrapContentSize(),
                text = key,
                fontSize = 15.sp,
                color = if (isError) Color.Red else Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            BasicTextField(
                value = mutableValue,
                onValueChange = {
                    val builder = StringBuilder()
                    var decimalAdded = false
                    var digitsAfterDecimal = 0
                    for (char in it) {
                        if (char.isDigit()) {
                            if (unitMeasure == "год" && builder.length >= 4) continue
                            if (decimalAdded) {
                                if (digitsAfterDecimal < 2) {
                                    builder.append(char)
                                    digitsAfterDecimal++
                                }
                            } else {
                                builder.append(char)
                            }
                        } else if (char == '.' && decimalAdded.not() && unitMeasure != "шт" && unitMeasure != "год") {
                            builder.append(char)
                            decimalAdded = true
                        }
                    }
                    val cleaned = builder.toString()
                    mutableValue = cleaned
                    mutablePlaceholder = ""
                    isError = false
                    onValueChanged(cleaned)
                },
                Modifier,
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = true,
                        placeholder = @Composable {
                            Text(text = mutablePlaceholder, fontSize = 15.sp, color = Color.Gray)
                        },
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(0.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                        )
                    )
                }
            )

            Text(
                modifier = Modifier.padding(start = 10.dp).wrapContentSize(),
                text = unitMeasure,
                fontSize = 15.sp,
                color = Color.Black
            )

        }

    }
}