package samaryanin.avitofork.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.navigation.VerificationScreen
import samaryanin.avitofork.presentation.screens.auth.data.AuthUpEvent
import samaryanin.avitofork.presentation.screens.auth.data.AuthUpState
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun SignUpPreview() {
    SignUpContent({ true }, {}, { AuthUpState() }, {}) // пустой обработчик
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param authViewModel модель обработки состояний регистрации
 * @param navHostController контроллер навигации
 */
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navHostController: NavHostController
) {

    val state by authViewModel.state.collectAsState()

    // обработчик выхода
    val onExit = {
        navHostController.popBackStack()
    }

    // обработчик авторизации
    val onLogin = {
        navHostController.navigate(VerificationScreen) {
            launchSingleTop = true
            restoreState = true
        }
    }

    // обработчик событий
    val handleEvent = { event: AuthUpEvent ->
        authViewModel.handleEvent(event)
    }

    SignUpContent(
        onExit = onExit,
        onLogin = onLogin,
        state = { state },
        handleEvent = handleEvent,
    )

}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param onExit обработчик навигации выхода
 * @param onLogin обработчик авторизации
 * @param state получение состояния
 * @param handleEvent обработчик событий
 */
@Composable
fun SignUpContent(
    onExit: () -> Boolean,
    onLogin: () -> Unit,
    state: () -> AuthUpState,
    handleEvent: (AuthUpEvent) -> Unit,
) {

    var email by remember { mutableStateOf(state.invoke().email) }
    var errorFrame by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Button(
                onClick = {
                    handleEvent(AuthUpEvent.CheckEmailFormValidation(email))
                    if (state.invoke().emailIsValid) {
                        onLogin()
                    }
                    else
                        errorFrame = true // ошибка валидации
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                    .imePadding()
                    .navigationBarsPadding(),
            ) {
                Text("Продолжить", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onExit()
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            AppTextTitle("Введите электронную почту")

            Space()

            AppTextFieldPlaceholder(
                value = email,
                onValueChange = {
                    email = it
                    errorFrame = false
                    handleEvent(AuthUpEvent.UpdateState(state.invoke().copy(email = email)))
                },
                placeholder = "helloworld@test.ru",
                errorListener = errorFrame
            )
            Spacer(modifier = Modifier.padding(2.dp))

            if (errorFrame) {
                Text(
                    "Некорректный адрес электронной почты ",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            Space()

        }
    }
}