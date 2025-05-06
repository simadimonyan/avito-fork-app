package samaryanin.avitofork.feature.messages.ui.navigation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
sealed class MessagesRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable
    object RouteID : MessagesRoutes("messages_id")

    /**
     * Окно списка сообщений (под BottomNavigation)
     */
    @Serializable
    object Messages : MessagesRoutes("messages")

    /**
     * Окно личных сообщений  (над BottomNavigation)
     * @param directId id чата
     */
    @Serializable
    @Immutable
    data class MessagesDirect(val directId: String) : MessagesRoutes("messages_direct")

}