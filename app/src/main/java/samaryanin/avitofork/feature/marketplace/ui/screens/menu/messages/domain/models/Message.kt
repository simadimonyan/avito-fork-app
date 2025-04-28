package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Message(
    val id: String = "",
    val user: String = "",
    val content: String = "",
    var timestamp: String = "",
    val state: MessageState = MessageState.SENT
)

enum class MessageState {
    RECEIVED, SENT, READ
}

