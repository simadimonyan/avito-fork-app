package samaryanin.avitofork.presentation.screens.auth

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import samaryanin.avitofork.presentation.navigation.StartScreen
import samaryanin.avitofork.presentation.screens.auth.data.AuthUpEvent
import samaryanin.avitofork.presentation.screens.auth.data.AuthUpState
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextBody
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppDigitsTextField
import samaryanin.avitofork.presentation.ui.theme.greyButton

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun VerificationPreview() {
    VerificationContent({ true }, {}, { AuthUpState() }, {}) // пустой обработчик
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param authViewModel модель обработки состояний регистрации
 * @param mainViewModel модель глобальной обработки состояний приложения
 * @param navHostController контроллер навигации
 */
@Composable
fun VerificationScreen(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {

    val state by authViewModel.state.collectAsState()

    // обработчик навигации выхода
    val onExit = {
        navHostController.popBackStack()
    }

    // обработчик навигации входа
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
    VerificationContent(
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
fun VerificationContent(
    onExit: () -> Boolean,
    onLogin: () -> Unit,
    state: () -> AuthUpState,
    handleEvent: (AuthUpEvent) -> Unit
) {

    // автоматическая отправка кода подтверждения при переходе на экран
    handleEvent(AuthUpEvent.SendVerificationCode)

    var code by remember { mutableStateOf("") }
    var errorFrame by remember { mutableStateOf(false) }
    var errorCodeIsNotValid by remember { mutableStateOf(false) }
    var sendCodeState by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Column {
                if (!sendCodeState) {
                    Button(
                        onClick = {
                            handleEvent(AuthUpEvent.SendVerificationCode)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = greyButton),
                        shape = RoundedCornerShape(10.dp),
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                    ) {
                        CountdownTimer {sendCodeState = true}
                    }
                }
                else {
                    Button(
                        onClick = {
                            handleEvent(AuthUpEvent.SendVerificationCode)
                            sendCodeState = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = greyButton),
                        shape = RoundedCornerShape(10.dp),
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                    ) {
                        Text("Отправить код", color = Color.Black, fontSize = 15.sp)
                    }
                }

                Button(
                    onClick = {
                        if (code.isBlank()) {
                            errorFrame = true
                        } else {
                            handleEvent(AuthUpEvent.CheckEmailCodeValidation(code))
                            errorCodeIsNotValid = !state.invoke().emailCodeIsValid

                            if (!errorCodeIsNotValid) {
                                onLogin()
                            } else
                                errorFrame = true
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
                    Text("Продолжить", fontSize = 15.sp)
                }
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
                append(state.invoke().email)
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
                    val update = state.invoke().copy(code = code)
                    handleEvent(AuthUpEvent.UpdateState(update))
                },
                errorListener = errorFrame
            )

            if (errorFrame) {
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