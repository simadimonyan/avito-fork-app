package samaryanin.avitofork.feature.settings.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.core.database.cache.CacheManager
import samaryanin.avitofork.core.ui.start.data.state.AppState
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.core.utils.FavoriteManager
import javax.inject.Inject

@Stable
@HiltViewModel
class SettingsViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val favoriteManager: FavoriteManager,
    private val cacheManager: CacheManager,
) : ViewModel() {

    fun logout() {
        favoriteManager.clearFavorites()
        cacheManager.clearAuthToken()

        appStateStore.appStateHolder.updateState(
            AppState(
                isFirstStartUp = false,
                isLoggedIn = false,
                authRequested = false
            )
        )

        appStateStore.authStateHolder.updateEmail("")
        appStateStore.authStateHolder.updateProfile("")
        appStateStore.authStateHolder.setCredentialsValid(false)
        appStateStore.authStateHolder.setEmailFieldValid(false)
        appStateStore.authStateHolder.setEmailCodeValid(false)
        appStateStore.authStateHolder.setPasswordValid(false)
        appStateStore.authStateHolder.setPostRegLoginError(false)
    }

}