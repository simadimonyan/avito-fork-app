package samaryanin.avitofork.feature.profile.ui.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import samaryanin.avitofork.app.activity.data.AppEvent
import samaryanin.avitofork.app.activity.data.AppState
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.feature.auth.ui.state.AuthState
import samaryanin.avitofork.feature.profile.ui.feature.profile.components.ProfileAuthorized
import samaryanin.avitofork.feature.profile.ui.feature.profile.components.ProfileUnauthorized
import samaryanin.avitofork.feature.profile.ui.navigation.profile.ProfileRoutes
import samaryanin.avitofork.feature.profile.ui.navigation.settings.SettingsRoutes
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileState
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel
import samaryanin.avitofork.shared.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.shared.ui.theme.navigationSelected

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
    globalNavController: NavController,
    navHostController: NavHostController,
) {

    val appState by mainViewModel.appStateHolder.appState.collectAsState()
    val authState by profileViewModel.authStateHolder.authState.collectAsState()
    val profileState by profileViewModel.profileStateHolder.profileState.collectAsState()
    val viewModel: ProfileViewModel = hiltViewModel()

    //сделать pullrefresh и продумать правильное обновление списка
    LaunchedEffect(Unit) { viewModel.refresh() }

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
        }
    }

    // обработчик событий для AuthBottomSheet
    val authRequest = {
        mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    ProfileContent(
        { appState },
        navigateTo,
        authRequest,
        { authState },
        { profileState },
        navHostController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    appState: () -> AppState,
    navigateTo: (Int) -> Unit,
    authRequest: () -> Unit,
    authState: () -> AuthState,
    profileState: () -> ProfileState,
    navHostController: NavHostController
) {

    val scrollState = rememberScrollState()

    val isNextEnabled by remember {
        derivedStateOf {
            scrollState.value > 0 // при прокрутке LazyColumn
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        contentColor = Color.White,
        containerColor = Color.White,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            if (appState().isLoggedIn) {
                ProfileAuthorized(scrollState, profileState, authState, navHostController)
            } else {
                ProfileUnauthorized(authRequest)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (isNextEnabled)
                            Modifier.shadow(
                                2.dp,
                                RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            )
                        else Modifier
                    ),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                TopAppBar(
                    modifier = Modifier,
                    windowInsets = WindowInsets(0),
                    title = {
                        AppTextTitle("Профиль")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    actions = {
                        IconButton(onClick = {
                            navigateTo(0) // 0 - индекс навигации на экран уведомлений
                        }) {
                            Icon(
                                modifier = Modifier.size(26.dp),
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Notifications",
                                tint = navigationSelected
                            )
                        }
                        IconButton(onClick = {
                            navigateTo(1) // 1 - индекс навигации на экран настроек
                        }) {
                            Icon(
                                modifier = Modifier.size(26.dp),
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                tint = navigationSelected
                            )
                        }
                    }
                )
            }
        }
    }
}