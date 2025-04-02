package samaryanin.avitofork.presentation.screens.menu.profile.data

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import samaryanin.avitofork.domain.model.post.PostState
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
@Immutable
data class ProfileState(

    /**
     * Список публикаций профиля
     */
    val posts: Map<String, List<PostState>> = mutableMapOf()

)

/**
 * State Holder паттерн
 */
@Singleton
@Immutable
class ProfileStateHolder @Inject constructor() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    fun updatePostsList(posts: Map<String, List<PostState>>) {
        _profileState.update { it.copy(posts = posts) }
    }

}