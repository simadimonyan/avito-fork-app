package samaryanin.avitofork.feature.auth.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import samaryanin.avitofork.R
import samaryanin.avitofork.core.ui.navigation.MainRoutes
import samaryanin.avitofork.core.ui.start.data.MainViewModel
import samaryanin.avitofork.core.ui.start.data.state.AppEvent
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.components.utils.text.AppTextBody
import samaryanin.avitofork.core.ui.utils.components.utils.text.AppTextTitle
import samaryanin.avitofork.core.ui.utils.components.utils.textField.AppDigitsTextField
import samaryanin.avitofork.core.ui.utils.theme.greyButton
import samaryanin.avitofork.feature.auth.ui.data.AuthEvent
import samaryanin.avitofork.feature.auth.ui.data.AuthState
import samaryanin.avitofork.feature.auth.ui.data.AuthViewModel
import samaryanin.avitofork.feature.auth.ui.navigation.AuthRoutes

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun VerificationPreview() {
    VerificationContent({}, {}, AuthState(), {}) // пустой обработчик
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param authViewModel модель обработки состояний регистрации
 * @param mainViewModel модель глобальной обработки состояний приложения
 * @param navHostController контроллер навигации
 * @param profileCreating условие для регистрации профиля
 */
@Composable
fun VerificationScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    profileCreating: Boolean = false
) {

    val state by authViewModel.appStateStore.authStateHolder.authState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // обработчик навигации выхода
    val onExit = {
        navHostController.popBackStack()
        keyboardController?.hide()
    }

    // обработчик навигации входа
    val onLogin = {
        navHostController.navigate(if (profileCreating) AuthRoutes.CreateProfile.route else MainRoutes.MainScreen.route) {
            if (!profileCreating) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
        keyboardController?.hide()
        if (!profileCreating) mainViewModel.handleEvent(AppEvent.ProfileHasLoggedIn)
    }

    // обработчик событий
    val handleEvent = { event: AuthEvent ->
        authViewModel.handleEvent(event)
    }

    // содержимое окна
    VerificationContent(
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
fun VerificationContent(
    onExit: () -> Unit?,
    onLogin: () -> Unit,
    state: AuthState,
    handleEvent: (AuthEvent) -> Unit
) {

    // автоматическая отправка кода подтверждения при переходе на экран
    handleEvent(AuthEvent.SendVerificationCode)

    var code by remember { mutableStateOf("") }
    var errorFrame by remember { mutableStateOf(false) }
    var errorCodeIsNotValid by remember { mutableStateOf(false) }
    var sendCodeState by remember { mutableStateOf(false) }

    LaunchedEffect(state.isLoading) {
        if (code.isNotEmpty() && !state.isLoading) {
            if (state.emailCodeIsValid) {
                onLogin()
            } else {
                errorCodeIsNotValid = true
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            Column {
                if (!sendCodeState) {
                    Button(
                        onClick = {
                            handleEvent(AuthEvent.SendVerificationCode)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = greyButton),
                        shape = RoundedCornerShape(10.dp),
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        CountdownTimer {sendCodeState = true}
                    }
                }
                else {
                    Button(
                        onClick = {
                            handleEvent(AuthEvent.SendVerificationCode)
                            sendCodeState = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = greyButton),
                        shape = RoundedCornerShape(10.dp),
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Text("Отправить код", color = Color.Black, fontSize = 15.sp)
                    }
                }

                Button(
                    onClick = {
                        if (code.isBlank()) {
                            errorFrame = true
                        } else {
                            handleEvent(AuthEvent.CheckEmailCodeValidation(state.email, code))
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
                    Text("Продолжить", fontSize = 15.sp)
                }
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
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onExit()
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            AppTextTitle("Подтвердите почту")

            Space()

            val message = buildAnnotatedString {
                append("Укажите проверочный код - он придет на ")
                pushStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                append(state.email)
                pop()
                append(" в течение 2 минут.")
            }

            AppTextBody(message)

            Space()

            AppDigitsTextField(
                value = code,
                placeholder = "Код",
                onValueChange = {
                    code = it
                    errorFrame = false
                    errorCodeIsNotValid = false
                },
                errorListener = errorFrame || errorCodeIsNotValid
            )

            if (errorFrame || errorCodeIsNotValid) {
                Text(
                    if (errorCodeIsNotValid) "Код недействительный" else "Необходимо ввести код подтверждения",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }

        }

    }
}

/**
 * Функция обратного отсчета для отправки кода подтверждения
 * -------------------------------------
 * @param totalTime секунды отсчета
 * @param onTimerFinished callback слушатель
 */
@SuppressLint("DefaultLocale")
@Composable
fun CountdownTimer(
    totalTime: Int = 120, // 2 минуты
    onTimerFinished: () -> Unit = {}
) {
    var timeLeft by remember { mutableIntStateOf(totalTime) }
    
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        } else {
            onTimerFinished()
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Text(
        text = formattedTime,
        fontSize = 18.sp
    )
}