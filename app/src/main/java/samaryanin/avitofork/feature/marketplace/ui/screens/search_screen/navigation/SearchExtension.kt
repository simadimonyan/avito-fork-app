package samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.additional_info_ad.AdditionalInfoScreen

/**
 * Расширение утилитной навигации поиска объявлений выше слоя BottomNavigation
 * ----------------------------------------------------------------------
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilSearchGraph(
    globalNavController: NavHostController
) {

    navigation(startDestination = SearchRoutes.AdditionalInfoScreen.route, route = SearchRoutes.RouteID.route) {

        composable( // Карточка товара
            route = SearchRoutes.AdditionalInfoScreen.route,
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
            AdditionalInfoScreen(globalNavController)
        }

    }

}