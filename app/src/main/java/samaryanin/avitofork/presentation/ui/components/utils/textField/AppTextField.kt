package samaryanin.avitofork.presentation.ui.components.utils.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.presentation.ui.theme.lightGrayColor

@Composable
fun AppDigitsTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value, onValueChange, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        placeholder = { Text(value, color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = lightGrayColor, focusedContainerColor = lightGrayColor,
            unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun AppTextFieldPlaceholder(value: String, onValueChange: (String) -> Unit, placeholder: String?) {
    TextField(
        value, onValueChange, shape = RoundedCornerShape(10.dp),
        placeholder = { Text("$placeholder", color = Color.Gray)},
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = lightGrayColor, focusedContainerColor = lightGrayColor,
            unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}