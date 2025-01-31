package samaryanin.avitofork.presentation.screens.start.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import samaryanin.avitofork.data.cache.CacheManager
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState

    fun handleEvent(event: AppEvent) {
        when(event) {
            is AppEvent.SaveAppState -> saveAppState()
            is AppEvent.RestoreCache -> restoreCache()
            is AppEvent.FirstStartUp -> setFirstStartUpSettings(event.isFirstStartUp)
            is AppEvent.ToggleAuthRequest -> toggleAuthRequest()
        }
    }

    private fun setFirstStartUpSettings(isFirstStartUp: Boolean) {
        _appState.update { it.copy(isFirstStartUp = isFirstStartUp) }
        saveAppState()
    }

    private fun toggleAuthRequest() {
        _appState.update { it.copy(authRequested = !it.authRequested) }
    }

    private fun saveAppState() {
        cacheManager.saveAppState(appState.value)
    }

    private fun restoreCache() {
        _appState.update { cacheManager.getAppState() }
    }

}