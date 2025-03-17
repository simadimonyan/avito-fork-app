package samaryanin.avitofork.presentation.screens.auth.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.screens.auth.data.AuthEvent
import samaryanin.avitofork.presentation.screens.auth.data.AuthState
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.auth.navigation.AuthRoutes
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun LoginPreview() {
    LoginContent({}, {}, AuthState(), {}) // пустой обработчик
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param authViewModel модель обработки состояний регистрации
 * @param navHostController контроллер навигации
 */
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navHostController: NavHostController
) {

    val state by authViewModel.appStateStore.authStateHolder.authState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.emailIsValid) {
        Log.d("UI", "Email валиден? ${state.emailIsValid}")
    }

    // обработчик выхода
    val onExit = {
        navHostController.popBackStack()
        keyboardController?.hide()
    }

    // обработчик авторизации
    val onLogin = {
        navHostController.navigate(AuthRoutes.Verification.createRoute(false)) {
            launchSingleTop = true
            restoreState = true
        }
        keyboardController?.hide()
    }

    // обработчик событий
    val handleEvent = { event: AuthEvent ->
        authViewModel.handleEvent(event)
    }

    // содержимое окна
    LoginContent(
        onExit = onExit,
        onLogin = onLogin,
        state = state,
        handleEvent = handleEvent
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
fun LoginContent(
    onExit: () -> Unit?,
    onLogin: () -> Unit?,
    state: AuthState,
    handleEvent: (AuthEvent) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var errorEmailBlank by remember { mutableStateOf(false) }
    var errorPassBlank by remember { mutableStateOf(false) }
    var emailErrorFrame by remember { mutableStateOf(false) }
    var errorWrongPass by remember { mutableStateOf(false) }

    var verificationEmail by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(verificationEmail) {
        if (state.email.isNotBlank()) emailErrorFrame = !state.emailIsValid
        verificationEmail = false
    }

    LaunchedEffect(state.isLoading) {
        if (password.isNotEmpty() && !state.isLoading) {
            if (state.credentialsAreValid) {
                onLogin()
            } else {
                errorWrongPass = true
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            Button(
                onClick = {
                    scope.launch {
                        if (state.email.isBlank()) {
                            errorEmailBlank = true
                        } else {
                            handleEvent(AuthEvent.CheckEmailFormValidation(state.email))
                            verificationEmail = true
                        }

                        if (!(emailErrorFrame && errorEmailBlank)) {
                            if (password.isBlank()) {
                                errorPassBlank = true
                            } else {
                                handleEvent(AuthEvent.VerifyAccountCredentials(state.email, password))
                            }
                        }
                    }
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
                Text("Войти", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onExit() }
                )
                Text("Забыли пароль?", modifier = Modifier.clickable { })
            }

            Spacer(modifier = Modifier.height(30.dp))

            AppTextTitle("Вход")

            Space()

            AppTextFieldPlaceholder(
                value = state.email,
                onValueChange = {
                    handleEvent(AuthEvent.UpdateEmailState(email = it))
                    emailErrorFrame = false
                    errorEmailBlank = false
                },
                placeholder = "Почта",
                errorListener = emailErrorFrame || errorEmailBlank
            )

            if (emailErrorFrame) {
                Text(
                    "Некорректный адрес электронной почты",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            if (errorEmailBlank) {
                Text(
                    "Необходимо ввести данные электронной почты",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            AppTextFieldPlaceholder(
                value = password,
                placeholder = "Пароль",
                isPassword = true,
                onValueChange = {
                    password = it
                    errorWrongPass = false
                    errorPassBlank = false
                },
                errorListener = errorWrongPass || errorPassBlank
            )

            if (errorWrongPass) {
                Text(
                    "Неправильный пароль или адрес электронной почты",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            if (errorPassBlank) {
                Text(
                    "Необходимо ввести пароль",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
        }
    }
}