package samaryanin.avitofork.presentation.screens.auth.signup

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
import androidx.compose.runtime.State
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
import kotlinx.coroutines.flow.MutableStateFlow
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.screens.auth.signup.data.SignUpEvent
import samaryanin.avitofork.presentation.screens.auth.signup.data.SignUpState
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder
import kotlin.reflect.KFunction1

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = false)
@Composable
fun SignUpPreview() {

    fun mockEventHandler(event: SignUpEvent) {} // заглушка

    @Composable
    fun mockGetState(): State<SignUpState> { // заглушка
        return MutableStateFlow(SignUpState()).collectAsState()
    }

    fun setMockState(state: SignUpState) {} // заглушка

    SignUpScreen({ true }, {}, ::mockEventHandler, mockGetState(), ::setMockState)
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param onExit внешний обработчик
 * @param navigateTo функция навигации
 * @param eventHandler обработчик событий
 * -------------------------------------
 */
@Composable
fun SignUpScreen(
    onExit: () -> Boolean,
    navigateTo: (Int) -> Unit,
    eventHandler: KFunction1<SignUpEvent, Unit>,
    getState: State<SignUpState>,
    setState: KFunction1<SignUpState, Unit>
) {

    val state by getState
    var email by remember { mutableStateOf(state.email) }
    var errorFrame by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Button(
                onClick = {
                    eventHandler(SignUpEvent.CheckEmailFormValidation(email))
                    if (state.emailIsValid) {
                        navigateTo(0) // 0 - индекс экрана верификации номера телефона
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
                placeholder = "helloworld@test.ru",
                onValueChange = {
                    email = it
                    errorFrame = false
                    setState(state.copy(email = email))
                },
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