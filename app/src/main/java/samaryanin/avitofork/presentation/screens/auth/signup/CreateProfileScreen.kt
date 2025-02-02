package samaryanin.avitofork.presentation.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.navigation.SignUp
import samaryanin.avitofork.presentation.navigation.StartScreen
import samaryanin.avitofork.presentation.screens.auth.data.AuthUpEvent
import samaryanin.avitofork.presentation.screens.auth.data.AuthUpState
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun CreateProfilePreview() {
    CreateProfileContent({ true }, {}, { AuthUpState() }, {}) // пустой обработчик
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

    val state by authViewModel.state.collectAsState()

    // обработчик выхода
    val onExit = {
        navHostController.popBackStack(route = SignUp, inclusive = false)
    }

    // обработчик авторизации
    val onLogin = {
        navHostController.navigate(StartScreen) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        mainViewModel.handleEvent(AppEvent.ProfileHasLoggedIn)
    }

    // обработчик событий
    val handleEvent = { event: AuthUpEvent ->
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
    onExit: () -> Boolean,
    onLogin: () -> Unit,
    state: () -> AuthUpState,
    handleEvent: (AuthUpEvent) -> Unit
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
        bottomBar = {
            Button(
                onClick = {

                    if (profile.isBlank()) {
                        errorProfileBlank = true // ошибка для пустого поля профиля
                    } else {
                        handleEvent(AuthUpEvent.CheckPasswordFormValidation(password))

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
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                    .imePadding()
                    .navigationBarsPadding(),
            ) {
                Text("Зарегистрироваться", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
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
                onValueChange = {
                    profile = it
                    errorProfileBlank = false
                    handleEvent(AuthUpEvent.UpdateState(state.invoke().copy(profile = profile)))
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
                onValueChange = {
                    password = it
                    errorPassFormat = false
                    errorPassBlank = false
                },
                errorListener = errorPassFormat || errorPassBlank
            )

            if (errorPassFormat) {
                Text(
                    "Пароль должен содержать минимум 8 символов, включая одну заглавную букву, одну цифру и один специальный символ",
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

            Spacer(modifier = Modifier.height(10.dp))

            AppTextFieldPlaceholder(
                value = repeatPassword,
                placeholder = "Повторите пароль",
                isPassword = true,
                onValueChange = {
                    repeatPassword = it
                    errorRepeatPassBlank = false
                    errorPassNotEquals = false
                },
                errorListener = errorRepeatPassBlank || errorPassNotEquals
            )

            if (errorPassNotEquals) {
                Text(
                    "Введенные пароли не равны",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

            if (errorRepeatPassBlank) {
                Text(
                    "Необходимо повторить пароль",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

        }

    }
}