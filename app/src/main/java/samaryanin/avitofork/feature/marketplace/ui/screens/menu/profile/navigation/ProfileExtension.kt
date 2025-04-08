package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import samaryanin.avitofork.feature.marketplace.ui.screens.notifications.NotificationsScreen
import samaryanin.avitofork.feature.settings.ui.SettingsScreen
import samaryanin.avitofork.feature.settings.ui.SettingsRoutes

/**
 * Расширение утилитной навигации профиля выше слоя BottomNavigation
 * -------------------------------------------------------------
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilProfileGraph(
    globalNavController: NavHostController,
) {

    navigation(startDestination = samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.navigation.ProfileRoutes.Notifications.route, route = samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.navigation.ProfileRoutes.RouteID.route) {

        composable( // Уведомления
            route = samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.navigation.ProfileRoutes.Notifications.route,
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