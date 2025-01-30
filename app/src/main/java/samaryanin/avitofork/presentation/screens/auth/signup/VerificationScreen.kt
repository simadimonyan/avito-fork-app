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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextBody
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.presentation.ui.components.utils.textField.AppTextFieldPlaceholder
import samaryanin.avitofork.presentation.screens.auth.signup.data.VerificationViewModel
import samaryanin.avitofork.presentation.ui.theme.lightGrayColor

/**
 * Функция для предпросмотра макета
 */
@Preview(showSystemUi = true)
@Composable
fun VerificationPreview() {
    VerificationCodeScreen { true } // пустой обработчик
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param onExit внешний обработчик
 */
@Composable
fun VerificationCodeScreen(onExit: () -> Unit){
    val viewModel: VerificationViewModel = hiltViewModel()

    val email by viewModel.state.collectAsState()
    val smsCode by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Column {
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = lightGrayColor),
                    shape = RoundedCornerShape(10.dp),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                ) {
                    Text("02:00", fontSize = 15.sp)
                }

                Button(
                    onClick = {

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
                .fillMaxSize()
                .padding(innerPadding)
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

            AppTextBody("Укажите проверочный код - он придет на ${email.phone} в течение 2 минут.")

            Space()

            AppTextFieldPlaceholder(
                value = smsCode,
                placeholder = "Код",
                onValueChange = {}
            )

        }

    }
}