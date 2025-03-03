package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.presentation.state.AppStateStore
import javax.inject.Inject

@HiltViewModel
class PosterViewModel @Inject constructor(
    val appStateStore: AppStateStore
) : ViewModel() {



}