package samaryanin.avitofork.feature.messages.ui.state

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import samaryanin.avitofork.feature.messages.domain.models.Chat
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
@Serializable
data class MessagesState(

    /**
     * Список чатов
     */
    val chats: MutableList<Chat> = mutableListOf()

)

/**
 * State Holder паттерн
 */
@Singleton
@Immutable
class MessageStateHolder @Inject constructor() {

    private val _messagesState = MutableStateFlow(MessagesState())
    val messagesState: StateFlow<MessagesState> = _messagesState

    fun updateChatList(chats: List<Chat>) {
        _messagesState.update { it.copy(chats = chats as MutableList<Chat>) }
    }

}