package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Chat
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.ChatScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel
import kotlin.reflect.typeOf

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

    navigation(startDestination = MessagesRoutes.MessagesDirect(Chat()).route, route = MessagesRoutes.RouteID.route) {

        composable<MessagesRoutes.MessagesDirect>(
            typeMap = mapOf(
                typeOf<Chat>() to ChatNavType.ChatType
            ),
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

            val json = Json {
                ignoreUnknownKeys = true
            }

            val chat = backStackEntry.arguments?.getString("chat")?.let {
                json.decodeFromString<Chat>(it)
            } ?: Chat()

            ChatScreen(globalNavController, viewModel, chat)

        }

    }

}