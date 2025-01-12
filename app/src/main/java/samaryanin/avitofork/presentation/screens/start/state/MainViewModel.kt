package samaryanin.avitofork.presentation.screens.start.state

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

    private val _appState = MutableStateFlow(UIAppState())
    val appState: StateFlow<UIAppState> = _appState

    fun handleEvent(event: AppEvent) {
        when(event) {
            is AppEvent.FirstStartUp -> setFirstStartUpSettings(event.isFirstStartUp)
            AppEvent.RestoreCache -> restoreCache()
        }
    }

    private fun setFirstStartUpSettings(isFirstStartUp: Boolean) {
        cacheManager.setFirstStartup(isFirstStartUp)
    }

    private fun restoreCache() {
        _appState.update { it.copy(isFirstStartUp = cacheManager.isFirstStartup()) }
    }

}