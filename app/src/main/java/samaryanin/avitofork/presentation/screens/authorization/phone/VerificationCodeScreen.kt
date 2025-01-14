package samaryanin.avitofork.presentation.screens.authorization.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import samaryanin.avitofork.presentation.customElements.text.AppTextBody
import samaryanin.avitofork.presentation.customElements.text.AppTextTitle
import samaryanin.avitofork.presentation.customElements.textField.AppDigitsTextField

@Composable
fun VerificationCodeScreen(){
    val globalNavController: NavHostController = rememberNavController()
    val viewModel: PhoneVerificationViewModel = hiltViewModel()

    var smsCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow),
            contentDescription = null,
            modifier = Modifier.size(32.dp).clickable {
                //nav.popBackStack()
            })

        Space()

        AppTextTitle("Подтвердите номер")
        AppTextBody("Укажите проверочный код - он придет на ${viewModel.state.value.phone} в течение 2 минут.")

        AppDigitsTextField(smsCode) {
            smsCode = it
            viewModel.updateCode(smsCode)
        }

        Button(
            onClick = {
                // globalNavController.navigate()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Продолжить")
        }
    }
}