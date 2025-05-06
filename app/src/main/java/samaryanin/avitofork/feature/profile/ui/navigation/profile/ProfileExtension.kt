package samaryanin.avitofork.feature.profile.ui.navigation.profile

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import samaryanin.avitofork.feature.profile.ui.feature.notifications.NotificationsScreen
import samaryanin.avitofork.feature.profile.ui.feature.settings.SettingsScreen
import samaryanin.avitofork.feature.profile.ui.navigation.settings.SettingsRoutes

/**
 * Расширение утилитной навигации профиля выше слоя BottomNavigation
 * -------------------------------------------------------------
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilProfileGraph(
    globalNavController: NavHostController,
) {

    navigation(startDestination = ProfileRoutes.Notifications.route, route = ProfileRoutes.RouteID.route) {

        composable( // Уведомления
            route = ProfileRoutes.Notifications.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            }
        ) {
            NotificationsScreen(globalNavController)
        }

        composable( // Настройки
            route = SettingsRoutes.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            }
        ) {
            SettingsScreen(globalNavController)
        }

    }

}