package samaryanin.avitofork.presentation.screens.auth.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.presentation.ui.theme.lightGrayColor

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun AuthBottomSheetPreview() {
    AuthBottomSheet(navigateTo = {}) {} // пустой обработчик
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param navigateTo внешний обработчик навигации
 * - 0 индекс экрана входа через телефон или почту
 * - 1 индекс экрана регистрации
 * @param onToggleAuthRequest внешний обработчик событий на меню авторизации
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthBottomSheet(navigateTo: (Int) -> Unit, onToggleAuthRequest: () -> Unit) {

    ModalBottomSheet(
        modifier = Modifier.wrapContentHeight(),
        sheetState = rememberModalBottomSheetState(),
        shape = RoundedCornerShape(17.dp),
        contentColor = Color.White,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Black) },
        onDismissRequest = onToggleAuthRequest
    ) {

        val annotatedText = buildAnnotatedString {
            append("При входе в приложение вы соглашаетесь с ")

            pushStyle(
                SpanStyle(
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline
                )
            )
            append("условиями использования")
            pop()

            append(" и ")

            pushStyle(
                SpanStyle(
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline
                )
            )
            append("политикой конфиденциальности.")
        }

        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                content = { Text(text = "Войти по почте", color = Color.White, fontSize = 16.sp) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                onClick = { navigateTo(0) } // 0 - индекс экрана входа через телефон или почту
            )
            Spacer(modifier = Modifier.height(1.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                content = { Text(text = "Зарегистрироваться", color = Color.Black, fontSize = 16.sp) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = lightGrayColor),
                onClick = { navigateTo(1) } // 1 - индекс экрана регистрации
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                text = annotatedText, //"При входе в приложение вы соглашаетесь с условиями использования TODO и политикой конфиденциальности.",
                color = Color.Gray,
                fontSize = 13.sp,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

    }

}