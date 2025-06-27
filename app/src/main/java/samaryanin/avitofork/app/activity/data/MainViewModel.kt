package samaryanin.avitofork.app.activity.data

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.favorites.domain.usecases.GetImageBytesByIdUseCase
import javax.inject.Inject

@Stable
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheManager: CacheManager,
    val appStateHolder: AppStateHolder,
    private val getImageBytesByIdUseCase: GetImageBytesByIdUseCase
) : ViewModel() {

    private val _imageCache = mutableMapOf<String, ByteArray>()
    val imageCache: Map<String, ByteArray> get() = _imageCache


    fun handleEvent(event: AppEvent) {
        when(event) {
            is AppEvent.SaveAppState -> saveAppState()
            is AppEvent.RestoreCache -> restoreCache()
            is AppEvent.FirstStartUp -> firstStartUp(event.isFirstStartUp)
            is AppEvent.ToggleAuthRequest -> appStateHolder.toggleAuthRequest()
            is AppEvent.ProfileHasLoggedIn -> appStateHolder.authorizeProfile()
        }
        saveAppState()
    }

    private fun firstStartUp(bool: Boolean) {
        appStateHolder.setFirstStartUpSettings(bool)
    }

    private fun saveAppState() {
        cacheManager.saveAppState(appStateHolder.appState.value)
    }

    private fun restoreCache() {
        val cachedState = cacheManager.getAppState()
        appStateHolder.updateState(cachedState)
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