package samaryanin.avitofork.presentation.screens.menu.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.AppState
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.theme.alphaLightBlue
import samaryanin.avitofork.presentation.ui.theme.lightBlue
import samaryanin.avitofork.presentation.ui.theme.navigationSelected

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun ProfilePreview() {
    ProfileContent({ AppState() }, {})
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param profileViewModel модуль обработки профиля
 * @param mainViewModel модель глобальной обработки состояний приложения
 * @param globalNavController глобальный контроллер навигации
 */
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    mainViewModel: MainViewModel,
    globalNavController: NavController
) {

    val appState by mainViewModel.appState.collectAsState()

    // обработчик событий для AuthBottomSheet
    val authRequest = {
        mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    ProfileContent({ appState }, authRequest)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(appState: () -> AppState, authRequest: () -> Unit) {
    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets(0),
        contentColor = Color.White,
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Профиль",
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                actions = {
                    IconButton(onClick = {
                        // TODO( навигация на окно уведомлений)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = navigationSelected
                        )
                    }
                    IconButton(onClick = {
                        // TODO( навигация в окно настроек )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = navigationSelected
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (appState.invoke().isLoggedIn) {
                ProfileAuthorized()
            }
            else {
                ProfileUnauthorized(authRequest)
            }
        }
    }
}

/**
 * Состояние экрана профиля когда пользователь авторизован
 */
@Composable
fun ProfileAuthorized() {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(),
        verticalArrangement = Arrangement.Top
    ) {



    }

}

/**
 * Состояние экрана профиля когда пользователь неавторизован
 */
@Composable
fun ProfileUnauthorized(authRequest: () -> Unit) {

    Column(modifier = Modifier
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