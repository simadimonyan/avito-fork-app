package samaryanin.avitofork.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.utilProfileGraph
import samaryanin.avitofork.presentation.screens.menu.search.navigation.SearchRoutes
import samaryanin.avitofork.presentation.screens.menu.search.navigation.utilSearchGraph
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
 * Расширение утилитной навигации Navigation Graph выше слоя BottomNavigation
 * ------------------------------------------------------------------------
 * @param mainViewModel главная модель приложения
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilGraph(
    mainViewModel: MainViewModel,
    globalNavController: NavHostController
) {
    navigation(startDestination = SearchRoutes.AdditionalInfoScreen.route, route = MainRoutes.UtilRouteID.route) {

        utilProfileGraph(globalNavController)
        utilSearchGraph(globalNavController)

    }
}

