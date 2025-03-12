package samaryanin.avitofork.presentation.screens.menu.messages.navigation

import kotlinx.serialization.Serializable
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.ProfileRoutes

@Serializable
sealed class MessagesRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : MessagesRoutes("messages_id")

    /**
     * Окно сообщений (под BottomNavigation)
     */
    @Serializable object Messages : MessagesRoutes("messages")

}