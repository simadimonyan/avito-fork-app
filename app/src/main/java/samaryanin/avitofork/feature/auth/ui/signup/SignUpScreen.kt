package samaryanin.avitofork.feature.auth.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.components.utils.text.AppTextTitle
import samaryanin.avitofork.core.ui.utils.components.utils.textField.AppTextFieldPlaceholder
import samaryanin.avitofork.feature.auth.ui.data.AuthEvent
import samaryanin.avitofork.feature.auth.ui.data.AuthState
import samaryanin.avitofork.feature.auth.ui.data.AuthViewModel
import samaryanin.avitofork.feature.auth.ui.navigation.AuthRoutes

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun SignUpPreview() {
    SignUpContent({}, {}, { AuthState() }, {}) // пустой обработчик
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

    val state by authViewModel.appStateStore.authStateHolder.authState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // обработчик выхода
    val onExit = {
        navHostController.popBackStack()
        keyboardController?.hide()
    }

    // обработчик авторизации
    val onLogin = {
        navHostController.navigate(AuthRoutes.Verification.createRoute(true)) {
            launchSingleTop = true
            restoreState = true
        }
        keyboardController?.hide()
    }

    // обработчик событий
    val handleEvent = { event: AuthEvent ->
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
    onExit: () -> Unit?,
    onLogin: () -> Unit?,
    state: () -> AuthState,
    handleEvent: (AuthEvent) -> Unit,
) {

    var email by remember { mutableStateOf(state.invoke().email) }
    var errorFrame by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            Button(
                onClick = {
                    handleEvent(AuthEvent.CheckEmailFormValidation(email))
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
                    .padding(
                        bottom = maxOf(
                            WindowInsets.ime
                                .asPaddingValues()
                                .calculateBottomPadding() - WindowInsets.navigationBars
                                .asPaddingValues()
                                .calculateBottomPadding(),
                            0.dp
                        )
                    )
            ) {
                Text("Продолжить", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
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
                modifier = Modifier,
                onValueChange = {
                    email = it
                    errorFrame = false
                    handleEvent(AuthEvent.UpdateEmailState(email = email))
                    handleEvent(AuthEvent.CheckEmailFormValidation(email))
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