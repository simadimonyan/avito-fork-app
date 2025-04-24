package samaryanin.avitofork.core.ui.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.core.ui.navigation.NestedScreenGraph
import samaryanin.avitofork.core.ui.start.data.state.AppEvent
import samaryanin.avitofork.core.ui.start.data.MainViewModel
import samaryanin.avitofork.feature.auth.ui.AuthBottomSheet
import samaryanin.avitofork.feature.auth.ui.navigation.AuthRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.appbar.BottomAppNavigation
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites.navigation.FavoriteRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.navigation.MessagesRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.navigation.ProfileRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.navigation.SearchRoutes

/**
 * Управляющий Composable
 */
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel,
    messagesViewModel: MessagesViewModel,
    globalNavHostController: NavHostController
) {
    val uiAppState by mainViewModel.appStateStore.appStateHolder.appState.collectAsState()
    val screenController = rememberNavController()
    var currentRoute by remember { mutableStateOf("search") }
    var selectedIndex by remember { mutableIntStateOf(0) }

    // обработчик событий изменения пути навигации
    LaunchedEffect(screenController) {
       screenController.addOnDestinationChangedListener { _, destination, _ ->
           currentRoute = destination.route.toString()
           selectedIndex = when (currentRoute) {
               SearchRoutes.Search.route -> 0
               FavoriteRoutes.Favorite.route -> 1
               MessagesRoutes.Messages.route -> 2
               ProfileRoutes.Profile.route -> 3
               else -> {
                   selectedIndex
               }
           }
       }
   }

    // обработчик событий для AuthBottomSheet
    val onToggleAuthRequest = {
        mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    // глобальный обработчик навигации приложения
    val mainScreenNavigateTo = { screen: Int ->
        when (screen) {
            0 -> { // 0 - индекс поиска объявлений
                screenController.navigate(SearchRoutes.Search.route) {
                    popUpTo(screenController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            1 -> { // 1 - индекс избранного
                screenController.navigate(FavoriteRoutes.Favorite.route) {
                    popUpTo(screenController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            2 -> { // 2 - индекс сообщений
                screenController.navigate(MessagesRoutes.Messages.route) {
                    popUpTo(screenController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            3 -> { // 3 - индекс профиля и управления объявлениями
                screenController.navigate(ProfileRoutes.Profile.route) {
                    popUpTo(screenController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    // обработчик навигации для AuthBottomSheet
    val navigateTo = { index: Int ->
        when (index) {
            0 -> { // 0 - индекс экрана входа через почту
                onToggleAuthRequest.invoke()
                globalNavHostController.navigate(AuthRoutes.Login.route) {
                    popUpTo(globalNavHostController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            1 -> { // 1 - индекс экрана регистрации
                onToggleAuthRequest.invoke()
                globalNavHostController.navigate(AuthRoutes.SignUp.route) {
                    popUpTo(globalNavHostController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), contentWindowInsets = WindowInsets(0)) {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).background(Color.White)) {

            NestedScreenGraph(screenController, mainViewModel, profileViewModel, messagesViewModel, globalNavHostController)

            if (uiAppState.authRequested) {
                AuthBottomSheet(navigateTo, onToggleAuthRequest)
            }

            BottomAppNavigation(uiAppState, mainScreenNavigateTo, onToggleAuthRequest, selectedIndex)
        }
    }
}