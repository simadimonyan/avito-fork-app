package samaryanin.avitofork.presentation.screens.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.presentation.navigation.Login
import samaryanin.avitofork.presentation.navigation.NestedGraph
import samaryanin.avitofork.presentation.navigation.SignUp
import samaryanin.avitofork.presentation.screens.auth.login.components.AuthBottomSheet
import samaryanin.avitofork.presentation.screens.menu.appbar.BottomAppNavigation
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
 * Управляющий Composable
 */
@Composable
fun StartScreen(
    viewModel: MainViewModel,
    globalGraph: NavHostController
) {
    val nestedGraph = rememberNavController()
    val uiAppState by viewModel.appState.collectAsState()

    // обработчик событий для AuthBottomSheet
    val onToggleAuthRequest = {
        viewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    // обработчик глобальной навигации для AuthBottomSheet
    val navigateTo = { index: Int ->
        floatArrayOf()
        when (index) {
            0 -> { // 0 - индекс экрана входа через телефон или почту
                onToggleAuthRequest.invoke()
                globalGraph.navigate(Login) {
                    popUpTo(globalGraph.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            1 -> { // 1 - индекс экрана регистрации
                onToggleAuthRequest.invoke()
                globalGraph.navigate(SignUp) {
                    popUpTo(globalGraph.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    // внутренний навигационный граф
    NestedGraph(navHostController = nestedGraph)

    if (uiAppState.authRequested) {
        AuthBottomSheet(navigateTo, onToggleAuthRequest)
    }

    BottomAppNavigation(uiAppState, onToggleAuthRequest)
}