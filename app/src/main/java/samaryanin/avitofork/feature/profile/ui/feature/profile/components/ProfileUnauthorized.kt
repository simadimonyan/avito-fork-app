package samaryanin.avitofork.feature.profile.ui.feature.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.R
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.alphaLightBlue
import samaryanin.avitofork.shared.ui.theme.lightBlue

/**
 * Состояние экрана профиля когда пользователь неавторизован
 */
@Composable
fun ProfileUnauthorized(authRequest: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = LocalContext.current.getString(R.string.unauthorized_profile),
            fontSize = 15.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        Space()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp)
                .height(40.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = alphaLightBlue),
            onClick = {
                authRequest()
            },
        ) {
            Text(
                text = "Войти или зарегистрироваться",
                fontSize = 15.sp,
                color = lightBlue,
                fontWeight = FontWeight.Normal
            )
        }
    }
}