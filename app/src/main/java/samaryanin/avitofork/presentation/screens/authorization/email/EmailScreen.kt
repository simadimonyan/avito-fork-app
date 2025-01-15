package samaryanin.avitofork.presentation.screens.authorization.email

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.customElements.space.Space
import samaryanin.avitofork.presentation.customElements.text.AppTextTitle
import samaryanin.avitofork.presentation.customElements.textField.AppTextFieldPlaceholder

@Composable
fun EmailScreen() {
    val globalNavController: NavHostController = rememberNavController()
    val viewModel: EmailScreenViewModel = hiltViewModel()

    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        //nav.popBackStack()
                    })
            Text("Забыли пароль?", modifier = Modifier.clickable { })
        }

        Space()

        AppTextTitle("Вход")
        Space()

        AppTextFieldPlaceholder(
            value = emailOrPhone,
            placeholder = "Телефон или почта",
            onValueChange = {
                emailOrPhone = it
                viewModel.updateLoginData(emailOrPhone, password)
            })

        Space()

        AppTextFieldPlaceholder(
            value = password,
            placeholder = "Пароль",
            onValueChange = {
                password = it
                viewModel.updateLoginData(emailOrPhone, password)
            })
        Space()

        Button(
            onClick = {
                Log.d("email", "e: ${viewModel.state.value.email} , p: ${viewModel.state.value.password }")
                // globalNavController.navigate()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Продолжить")
        }
    }
}