package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState

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