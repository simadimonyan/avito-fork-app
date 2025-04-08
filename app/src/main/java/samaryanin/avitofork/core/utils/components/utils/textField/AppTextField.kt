package samaryanin.avitofork.core.utils.components.utils.textField

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R
import samaryanin.avitofork.core.utils.theme.lightGrayColor

@Composable
fun AppDigitsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorListener: Boolean
) {
    TextField(
        value,
        onValueChange,
        isError = errorListener,
        maxLines = 1,
        singleLine = true,

        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(placeholder, color = Color.Gray)
        },

        colors = TextFieldDefaults.colors(

            unfocusedContainerColor = lightGrayColor,
            unfocusedIndicatorColor = Color.Transparent,

            focusedContainerColor = lightGrayColor,
            focusedIndicatorColor = Color.Transparent,

            errorPlaceholderColor = lightGrayColor,
            errorContainerColor = lightGrayColor,
            errorIndicatorColor = Color.Transparent,
            errorCursorColor = Color.Red,
            errorLabelColor = Color.Red

        ),

        modifier = if (errorListener) {
            Modifier.fillMaxWidth()
                .border(
                    1.dp,
                    color = Color.Red,
                    shape = RoundedCornerShape(10.dp)
                )
        }
        else
            Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun AppTextFieldPlaceholder(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String?,
    errorListener: Boolean,
    isPassword: Boolean = false,
    modifier: Modifier
) {

    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value,
        onValueChange,
        isError = errorListener,
        maxLines = 1,
        singleLine = true,

        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation()
            else VisualTransformation.None,

        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text("$placeholder", color = Color.Gray)
        },

        trailingIcon = {
            if (isPassword) {
                val icon = if (passwordVisible) painterResource(R.drawable.ic_pass_view) else painterResource(R.drawable.ic_pass_hide)
                val contentDescription = if (passwordVisible) "Hide password" else "Show password"
                Image(
                    painter = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .size(25.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                passwordVisible = !passwordVisible
                            })
                        }
                )
            }
        },

        colors = TextFieldDefaults.colors(

            unfocusedContainerColor = lightGrayColor,
            unfocusedIndicatorColor = Color.Transparent,

            focusedContainerColor = lightGrayColor,
            focusedIndicatorColor = Color.Transparent,

            errorPlaceholderColor = lightGrayColor,
            errorContainerColor = lightGrayColor,
            errorIndicatorColor = Color.Transparent,
            errorCursorColor = Color.Red,
            errorLabelColor = Color.Red

        ),

        modifier = if (errorListener) {
            modifier.fillMaxWidth()
                .border(
                    1.dp,
                    color = Color.Red,
                    shape = RoundedCornerShape(10.dp)
                )
        }
        else
            modifier.fillMaxWidth()

    )

}