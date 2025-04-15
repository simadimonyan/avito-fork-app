package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Message(
    val id: String,
    val user: String,
    val content: String,
    val timestamp: String,
    val state: MessageState
)

enum class MessageState {
    RECEIVED, SENT, READ
}