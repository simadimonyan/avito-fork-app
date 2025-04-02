package samaryanin.avitofork.presentation.screens.menu.profile.data

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.domain.model.post.PostState
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryState

sealed class ProfileEvent {

    /**
     * Загрузить публикации профиля
     */
    object LoadPublications : ProfileEvent()

    /**
     * Добавить публикацию в список
     */
    @Immutable
    data class AddPublication(val post: PostState) : ProfileEvent()

}