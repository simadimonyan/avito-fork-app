package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.ChatScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel

/**
 * Расширение навигации авторизации Navigation Graph выше слоя BottomSheet
 * ----------------------------------------------
 * @param viewModel модель сообщений
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilMessagesGraph(
    viewModel: MessagesViewModel,
    globalNavController: NavHostController,
) {

    navigation(startDestination = MessagesRoutes.MessagesDirect("").route, route = MessagesRoutes.RouteID.route) {

        composable<MessagesRoutes.MessagesDirect>(
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
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("directId") ?: ""
            ChatScreen(viewModel, id)

        }

    }

}