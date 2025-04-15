package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState

@Immutable
data class Chat(

    /**
     * ID чата
     */
    val chatId: String = "",

    /**
     * Имя аккаунта автора публикации
     */
    val profileName: String = "",

    /**
     * Список сообщений
     */
    val messages: List<Message> = mutableListOf(),

    /**
     * Референс на публикацию
     */
    val postReference: PostState = PostState()

)

