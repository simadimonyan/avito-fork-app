package samaryanin.avitofork.presentation.screens.start.state

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : ViewModel() {

    fun handleEvent(event: AppEvent) {
        when(event) {
            is AppEvent.FirstStartUp -> setFirstStartUpSettings(event.isFirstStartUp)
        }
    }

    private fun setFirstStartUpSettings(isFirstStartUp: Boolean) {

    }

}