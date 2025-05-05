package samaryanin.avitofork.feature.messages.ui.state

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.messages.domain.models.Message

sealed class MessagesEvent {

    /**
     * Отправить сообщение
     */
    @Immutable
    data class SendMessage(val chatId: String, val message: Message) : MessagesEvent()

    /**
     * Обновить список чатов
     */
    object ChatsRefresh : MessagesEvent()

}