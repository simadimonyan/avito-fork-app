package samaryanin.avitofork.feature.profile.ui.state.profile

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.poster.domain.models.PostState

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