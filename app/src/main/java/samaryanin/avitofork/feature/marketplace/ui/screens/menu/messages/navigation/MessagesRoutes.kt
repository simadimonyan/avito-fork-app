package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.navigation

import kotlinx.serialization.Serializable

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