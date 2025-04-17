package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import javax.inject.Inject

@Stable
@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val appStateStore: AppStateStore
) : ViewModel() {

    fun handleEvent(event: MessagesEvent) {

    }



}