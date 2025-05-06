package samaryanin.avitofork.feature.messages.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import samaryanin.avitofork.feature.poster.domain.models.PostState

@Immutable
@Serializable
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
    val messages: MutableList<Message> = mutableListOf(),

    /**
     * Референс на публикацию
     */
    val postReference: PostState = PostState()

)