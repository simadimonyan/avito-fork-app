package samaryanin.avitofork.feature.auth.ui.signup

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.core.navigation.MainRoutes
import samaryanin.avitofork.core.ui.start.data.MainViewModel
import samaryanin.avitofork.core.ui.start.data.state.AppEvent
import samaryanin.avitofork.core.utils.components.utils.space.Space
import samaryanin.avitofork.core.utils.components.utils.text.AppTextTitle
import samaryanin.avitofork.core.utils.components.utils.textField.AppTextFieldPlaceholder
import samaryanin.avitofork.feature.auth.ui.data.AuthEvent
import samaryanin.avitofork.feature.auth.ui.data.AuthState
import samaryanin.avitofork.feature.auth.ui.data.AuthViewModel
import samaryanin.avitofork.feature.auth.ui.navigation.AuthRoutes

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun CreateProfilePreview() {
    CreateProfileContent({}, {}, { AuthState() }, {}) // пустой обработчик
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param authViewModel модель обработки состояний регистрации
 * @param mainViewModel модель глобальной обработки состояний приложения
 * @param navHostController контроллер навигации
 */
@Composable
fun CreateProfileScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {

    val state by authViewModel.appStateStore.authStateHolder.authState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // обработчик выхода
    val onExit = {
        navHostController.popBackStack(route = AuthRoutes.SignUp.route, inclusive = false)
        keyboardController?.hide()
    }

    // обработчик авторизации
    val onLogin = {
        navHostController.navigate(MainRoutes.MainScreen.route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        keyboardController?.hide()
        mainViewModel.handleEvent(AppEvent.ProfileHasLoggedIn)
    }

    // обработчик событий
    val handleEvent = { event: AuthEvent ->
        authViewModel.handleEvent(event)
    }

    // содержимое окна
    CreateProfileContent(
        onExit = onExit,
        onLogin = onLogin,
        state = { state },
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
fun CreateProfileContent(
    onExit: () -> Unit?,
    onLogin: () -> Unit,
    state: () -> AuthState,
    handleEvent: (AuthEvent) -> Unit
) {

    var profile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var errorProfileBlank by remember { mutableStateOf(false) }
    var errorPassBlank by remember { mutableStateOf(false) }
    var errorRepeatPassBlank by remember { mutableStateOf(false) }
    var errorPassFormat by remember { mutableStateOf(false) }
    var errorPassNotEquals by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            Button(
                onClick = {
                    if (profile.isBlank()) {
                        errorProfileBlank = true // ошибка для пустого поля профиля
                    } else {
                        if (password.isBlank()) {
                            errorPassBlank = true // ошибка для пустого поля пароля
                        }
                        else {
                            handleEvent(AuthEvent.CheckPasswordFormValidation(password))

                            if (!state.invoke().passwordFormIsValid) {
                                errorPassFormat = true // ошибка на неверный формат пароля
                            } else {
                                if (repeatPassword.isBlank()) {
                                    errorRepeatPassBlank = true // ошибка на пустой повтор пароля
                                } else {
                                    if (password != repeatPassword) {
                                        errorPassNotEquals = true // ошибка на несовпадение паролей
                                    } else {
                                        onLogin()
                                    }
                                }
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
                Text("Зарегистрироваться", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onExit()
                        }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            AppTextTitle("Регистрация")
            Space()

            AppTextFieldPlaceholder(
                value = profile,
                modifier = Modifier,
                onValueChange = {
                    profile = it
                    errorProfileBlank = false
                    handleEvent(AuthEvent.UpdateProfileState(profile = profile))
                    handleEvent(AuthEvent.CheckPasswordFormValidation(password))
                },
                placeholder = "Имя для профиля",
                errorListener = errorProfileBlank
            )

            if (errorProfileBlank) {
                Text(
                    "Необходимо ввести имя аккаунта",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            AppTextFieldPlaceholder(
                value = password,
                placeholder = "Пароль",
                isPassword = true,
                modifier = Modifier,
                onValueChange = {
                    password = it
                    handleEvent(AuthEvent.CheckPasswordFormValidation(password))
                    errorPassFormat = false
                    errorPassBlank = false
                },
                errorListener = errorPassFormat || errorPassBlank
            )

            if (errorPassBlank) {
                Text(
                    "Необходимо ввести пароль",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            if (errorPassFormat) {
                Text(
                    "Пароль должен содержать минимум 8 символов, включая одну заглавную букву, одну цифру и один специальный символ",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            AppTextFieldPlaceholder(
                value = repeatPassword,
                placeholder = "Повторите пароль",
                modifier = Modifier,
                isPassword = true,
                onValueChange = {
                    repeatPassword = it
                    errorRepeatPassBlank = false
                    errorPassNotEquals = false
                },
                errorListener = errorRepeatPassBlank || errorPassNotEquals
            )

            if (errorRepeatPassBlank) {
                Text(
                    "Необходимо повторить пароль",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            if (errorPassNotEquals) {
                Text(
                    "Введенные пароли не совпадают",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

        }

    }
}