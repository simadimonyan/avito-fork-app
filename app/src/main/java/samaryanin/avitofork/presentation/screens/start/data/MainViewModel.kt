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
            is AppEvent.ProfileHasLoggedIn -> authorizeProfile()
        }
    }

    private fun setFirstStartUpSettings(isFirstStartUp: Boolean) {
        _appState.update { it.copy(isFirstStartUp = isFirstStartUp) }
        saveAppState()
    }

    private fun authorizeProfile() {
        _appState.update { it.copy(isLoggedIn = true) }
        saveAppState()
    }

    private fun toggleAuthRequest() {
        _appState.update { it.copy(authRequested = !it.authRequested) }
    }

    private fun saveAppState() {
        cacheManager.saveAppState(_appState.value)
    }

    private fun restoreCache() {
        val cachedState = cacheManager.getAppState()
        _appState.update { cachedState }
    }

}