package samaryanin.avitofork.core.ui.start.data

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.database.cache.CacheManager
import samaryanin.avitofork.core.ui.start.data.state.AppEvent
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import javax.inject.Inject

@Stable
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheManager: CacheManager,
    val appStateStore: AppStateStore
) : ViewModel() {

    fun handleEvent(event: AppEvent) {
        when(event) {
            is AppEvent.SaveAppState -> saveAppState()
            is AppEvent.RestoreCache -> restoreCache()
            is AppEvent.FirstStartUp -> firstStartUp(event.isFirstStartUp)
            is AppEvent.ToggleAuthRequest -> appStateStore.appStateHolder.toggleAuthRequest()
            is AppEvent.ProfileHasLoggedIn -> appStateStore.appStateHolder.authorizeProfile()
        }
        saveAppState()
    }

    private fun firstStartUp(bool: Boolean) {
        appStateStore.appStateHolder.setFirstStartUpSettings(bool)
    }

    private fun saveAppState() {
        cacheManager.saveAppState(appStateStore.appStateHolder.appState.value)
    }

    private fun restoreCache() {
        val cachedState = cacheManager.getAppState()
        appStateStore.appStateHolder.updateState(cachedState)
    }

}