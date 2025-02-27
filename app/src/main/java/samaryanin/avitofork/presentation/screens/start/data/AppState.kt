package samaryanin.avitofork.presentation.screens.start.data

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Stable
data class AppState(

    /**
    * Глобальные настройки приложения
    */
    val isFirstStartUp: Boolean = true,
    val isLoggedIn: Boolean = false,

    /**
     * Событийные состояния
     */
    val authRequested: Boolean = false

)

/**
 * State Holder паттерн
 */
@Singleton
class AppStateHolder @Inject constructor() {

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState

    fun updateState(state: AppState) {
        _appState.update { state }
    }

    fun setFirstStartUpSettings(isFirstStartUp: Boolean) {
        _appState.update { it.copy(isFirstStartUp = isFirstStartUp) }
    }

    fun authorizeProfile() {
        _appState.update { it.copy(isLoggedIn = true) }
    }

    fun toggleAuthRequest() {
        _appState.update { it.copy(authRequested = !it.authRequested) }
    }

}