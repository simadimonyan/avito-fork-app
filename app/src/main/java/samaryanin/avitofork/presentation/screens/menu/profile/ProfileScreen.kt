package samaryanin.avitofork.presentation.screens.menu.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.screens.auth.data.AuthState
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.AppState
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel
import samaryanin.avitofork.presentation.screens.menu.profile.components.AddProfile
import samaryanin.avitofork.presentation.screens.menu.profile.components.DefaultAvatar
import samaryanin.avitofork.presentation.screens.menu.profile.components.ProfileTabLayout
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileState
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.ProfileRoutes
import samaryanin.avitofork.presentation.screens.menu.profile.poster.navigation.PostRoutes
import samaryanin.avitofork.presentation.screens.settings.navigation.SettingsRoutes
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
    ProfileContent({ AppState() }, {}, {}, { AuthState() }) { ProfileState() }
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

    val appState by mainViewModel.appStateStore.appStateHolder.appState.collectAsState()
    val authState by mainViewModel.appStateStore.authStateHolder.authState.collectAsState()
    val profileState by profileViewModel.appStateStore.profileStateHolder.profileState.collectAsState()

    val navigateTo = { index: Int ->
        when (index) {
            0 -> { // 0 - индекс навигации на экран уведомлений
                globalNavController.navigate(ProfileRoutes.Notifications.route) {
                    popUpTo(globalNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
            1 -> { // 1 - индекс навигации на экран настроек
                globalNavController.navigate(SettingsRoutes.Settings.route) {
                    popUpTo(globalNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
            2 -> { // 2 - индекс навигации на меню управления объявлениями
                globalNavController.navigate(PostRoutes.PostCategories.route) {
                    popUpTo(globalNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        }
    }

    // обработчик событий для AuthBottomSheet
    val authRequest = {
        mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    ProfileContent({ appState }, navigateTo, authRequest, { authState }, { profileState })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    appState: () -> AppState,
    navigateTo: (Int) -> Unit,
    authRequest: () -> Unit,
    authState: () -> AuthState,
    profileState: () -> ProfileState
) {
    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets(0),
        contentColor = Color.White,
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(3.dp),
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Профиль",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                actions = {
                    IconButton(onClick = {
                        navigateTo(0) // 0 - индекс навигации на экран уведомлений
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = navigationSelected
                        )
                    }
                    IconButton(onClick = {
                        navigateTo(1) // 1 - индекс навигации на экран настроек
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
                ProfileAuthorized(profileState, authState, navigateTo)
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
fun ProfileAuthorized(
    profileState: () -> ProfileState,
    authState: () -> AuthState,
    navigateTo: (Int) -> Unit
) {

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(0),
        ) {
            var name = authState.invoke().profile
            name = if (name != "") name else "Тестовое имя"

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp)) {
                DefaultAvatar(name = name)
                Space()
                AddProfile()
            }

            Space(10.dp)

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = name,
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Space()

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "На Avito Fork с 2010 года",
                fontSize = 15.sp,
                color = Color.Black,
            )

            Space(2.dp)

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Частное лицо",
                fontSize = 15.sp,
                color = Color.Black,
            )

            Space(2.dp)

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "ID: 123456789",
                fontSize = 15.sp,
                color = Color.Black,
            )

            Space()
        }

        ProfileTabLayout(profileState().posts)

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                navigateTo(2) // 2 - индекс навигации на меню управления объявлениями
            },
            modifier = Modifier
                .padding(bottom = 70.dp)
                .align(Alignment.BottomEnd),
            containerColor = navigationSelected
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
        }
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