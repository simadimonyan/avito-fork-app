package samaryanin.avitofork.feature.feed.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import samaryanin.avitofork.feature.feed.ui.feature.card.AdditionalInfoScreen

/**
 * Расширение утилитной навигации поиска объявлений выше слоя BottomNavigation
 * ----------------------------------------------------------------------
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilSearchGraph(
    globalNavController: NavHostController
) {

    navigation(startDestination = FeedRoutes.AdditionalInfoScreen.route, route = FeedRoutes.RouteID.route) {

        composable( // Карточка товара
            route = FeedRoutes.AdditionalInfoScreen.route,
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