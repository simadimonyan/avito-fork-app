package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.domain.MessagesUseCase
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message
import javax.inject.Inject

@Stable
@HiltViewModel
class MessagesViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val messagesUseCase: MessagesUseCase
) : ViewModel() {

    fun handleEvent(event: MessagesEvent) {
        when (event) {
            is MessagesEvent.SendMessage -> sendMessage(event.chatId, event.message)
            is MessagesEvent.ChatsRefresh -> refresh()
        }
    }

    private fun sendMessage(chatId: String, message: Message) {
        val currentState = appStateStore.messageStateHolder.messagesState.value

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

        appStateStore.messageStateHolder.updateChatList(updatedChats)
    }

    private fun refresh() {
        appStateStore.messageStateHolder.updateChatList(messagesUseCase.loadChatsUseCase.loadChats())
    }

}