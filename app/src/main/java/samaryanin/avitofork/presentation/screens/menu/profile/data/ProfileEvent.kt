package samaryanin.avitofork.presentation.screens.menu.profile.data

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
    data class AddPublication(val post: PostState) : ProfileEvent()

}