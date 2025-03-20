package samaryanin.avitofork.presentation.screens.menu.profile.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.domain.usecase.posts.ConfigurationUseCase
import samaryanin.avitofork.presentation.state.AppStateStore
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val appStateStore: AppStateStore
): ViewModel() {

    fun handleEvent() {



    }


}