package samaryanin.avitofork.presentation.screens.menu.profile.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.domain.model.post.PostState
import samaryanin.avitofork.presentation.state.AppStateStore
import javax.inject.Inject

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
        val map = appStateStore.profileStateHolder.profileState.value.posts.toMutableMap()

        val list: MutableList<PostState> = map["0"]?.toMutableList() ?: mutableListOf()
        list.add(post)
        map["0"] = list

        appStateStore.profileStateHolder.updatePostsList(map)
    }

    private fun loadPublications() {
        // TODO (загрузка публикаций с сервера)
    }

}