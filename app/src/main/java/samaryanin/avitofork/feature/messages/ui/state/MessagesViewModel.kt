package samaryanin.avitofork.feature.messages.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.feature.messages.domain.models.Message
import samaryanin.avitofork.feature.messages.domain.usecases.LoadChatsUseCase
import javax.inject.Inject

@Stable
@HiltViewModel
class MessagesViewModel @Inject constructor(
    val messageStateHolder: MessageStateHolder,
    private val loadChatsUseCase: LoadChatsUseCase
) : ViewModel() {

    fun handleEvent(event: MessagesEvent) {
        when (event) {
            is MessagesEvent.SendMessage -> sendMessage(event.chatId, event.message)
            is MessagesEvent.ChatsRefresh -> refresh()
        }
    }

    private fun sendMessage(chatId: String, message: Message) {
        val currentState = messageStateHolder.messagesState.value

        val newMessage = message.copy(
            user = "myId",
            timestamp = System.currentTimeMillis().toString()
        )

        val updatedChats = currentState.chats.map { chat ->
            if (chat.chatId == chatId) {
                val updatedMessages = chat.messages + newMessage
                chat.copy(messages = updatedMessages.toMutableList())
            } else chat
        }.toMutableList()

        messageStateHolder.updateChatList(updatedChats)
    }

    private fun refresh() {
        messageStateHolder.updateChatList(loadChatsUseCase.loadChats())
    }

}