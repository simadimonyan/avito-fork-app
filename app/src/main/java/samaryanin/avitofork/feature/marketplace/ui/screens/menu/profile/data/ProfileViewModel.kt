package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor(
    val appStateStore: AppStateStore
): ViewModel() {

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadPublications -> loadPublications()
            is ProfileEvent.AddPublication -> addPublication(event.post)
        }
    }

    private fun addPublication(post: PostState) {
        val currentPosts = appStateStore.profileStateHolder.profileState.value.posts
        val map = HashMap(currentPosts)
        val list = ArrayList(map["0"] ?: emptyList())

        list.add(post)
        map["0"] = list

        appStateStore.profileStateHolder.updatePostsList(map)

    }

    private fun loadPublications() {
        // TODO (загрузка публикаций с сервера)
    }

}