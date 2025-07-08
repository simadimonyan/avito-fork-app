package samaryanin.avitofork.feature.profile.ui.state.profile

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.feature.auth.ui.state.AuthStateHolder
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.favorites.domain.usecases.GetUsersAdsUseCase
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor(
    val appStateHolder: AppStateHolder,
    val authStateHolder: AuthStateHolder,
    val profileStateHolder: ProfileStateHolder,
    val getUsersAdsUseCase: GetUsersAdsUseCase,
) : ViewModel() {

    private val _userAds = MutableStateFlow<List<Ad>>(emptyList())
    val userAds: StateFlow<List<Ad>> = _userAds.asStateFlow()

    init {
        safeScope.launch {
            _userAds.update { getUsersAdsUseCase() }
        }
    }

    fun refresh(){
        safeScope.launch {
            _userAds.update { getUsersAdsUseCase() }
        }
    }

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadPublications -> loadPublications()
            is ProfileEvent.AddPublication -> addPublication(event.post)
        }
    }

    private fun addPublication(post: PostState) {
        val currentPosts = profileStateHolder.profileState.value.posts
        val map = HashMap(currentPosts)
        val list = ArrayList(map["0"] ?: emptyList())

        list.add(post)
        map["0"] = list

        profileStateHolder.updatePostsList(map)

    }

    private fun loadPublications() {
        // TODO (загрузка публикаций с сервера)
    }

}