package samaryanin.avitofork.presentation.screens.start

import android.util.Log
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
import samaryanin.avitofork.presentation.navigation.AuthRoutes
import samaryanin.avitofork.presentation.navigation.NestedScreenGraph
import samaryanin.avitofork.presentation.navigation.ProfileRoutes
import samaryanin.avitofork.presentation.navigation.SearchRoutes
import samaryanin.avitofork.presentation.screens.auth.AuthBottomSheet
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.menu.appbar.BottomAppNavigation
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
 * Управляющий Composable
 */
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    authViewModel: AuthViewModel,
    globalNavHostController: NavHostController
) {
    val uiAppState by viewModel.appState.collectAsState()
    val screenController = rememberNavController()
    var currentRoute by remember { mutableStateOf("search") }
    var selectedIndex by remember { mutableIntStateOf(0) }

    // обработчик событий изменения пути навигации
    LaunchedEffect(screenController) {
       screenController.addOnDestinationChangedListener { _, destination, _ ->
           currentRoute = destination.route.toString()
           selectedIndex = when (currentRoute) {
               SearchRoutes.Search.route -> 0
               SearchRoutes.ShoppingCart.route -> 1
               ProfileRoutes.Profile.route -> 3
               else -> {
                   selectedIndex
               }
           }
       }
   }

    // обработчик событий для AuthBottomSheet
    val onToggleAuthRequest = {
        viewModel.handleEvent(AppEvent.ToggleAuthRequest)
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
                screenController.navigate(SearchRoutes.ShoppingCart.route) { // TODO (избранное)
                    popUpTo(screenController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            2 -> { // 2 - индекс сообщений
                screenController.navigate(SearchRoutes.ShoppingCart.route) { // TODO (сообщения)
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

            NestedScreenGraph(screenController, authViewModel, viewModel, globalNavHostController)

            if (uiAppState.authRequested) {
                AuthBottomSheet(navigateTo, onToggleAuthRequest)
            }

            BottomAppNavigation(uiAppState, mainScreenNavigateTo, onToggleAuthRequest, selectedIndex)
        }
    }
}