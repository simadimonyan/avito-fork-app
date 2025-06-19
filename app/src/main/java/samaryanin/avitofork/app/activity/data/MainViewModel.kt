package samaryanin.avitofork.app.activity.data

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.shared.state.AppStateStore
import samaryanin.avitofork.feature.favorites.domain.usecases.GetImageBytesByIdUseCase
import javax.inject.Inject

@Stable
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheManager: CacheManager,
    val appStateStore: AppStateStore,
    private val getImageBytesByIdUseCase: GetImageBytesByIdUseCase
) : ViewModel() {

    private val _imageCache = mutableMapOf<String, ByteArray>()
    val imageCache: Map<String, ByteArray> get() = _imageCache


    fun handleEvent(event: AppEvent) {
        when(event) {
            is AppEvent.SaveAppState -> saveAppState()
            is AppEvent.RestoreCache -> restoreCache()
            is AppEvent.FirstStartUp -> firstStartUp(event.isFirstStartUp)
            is AppEvent.ToggleAuthRequest -> appStateStore.appState.toggleAuthRequest()
            is AppEvent.ProfileHasLoggedIn -> appStateStore.appState.authorizeProfile()
        }
        saveAppState()
    }

    private fun firstStartUp(bool: Boolean) {
        appStateStore.appState.setFirstStartUpSettings(bool)
    }

    private fun saveAppState() {
        cacheManager.saveAppState(appStateStore.appState.appState.value)
    }

    private fun restoreCache() {
        val cachedState = cacheManager.getAppState()
        appStateStore.appState.updateState(cachedState)
    }

    suspend fun loadImage(id: String): ByteArray? {
        return _imageCache[id] ?: try {
            val bytes = getImageBytesByIdUseCase.invoke(id)
            _imageCache[id] = bytes
            bytes
        } catch (_: Exception) {
            null
        }
    }

}