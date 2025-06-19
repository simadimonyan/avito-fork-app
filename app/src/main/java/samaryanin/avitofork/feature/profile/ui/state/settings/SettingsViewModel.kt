package samaryanin.avitofork.feature.profile.ui.state.settings

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.app.activity.data.AppState
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.favorites.data.FavoriteManager
import samaryanin.avitofork.shared.state.AppStateStore
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

        appStateStore.appState.updateState(
            AppState(
                isFirstStartUp = false,
                isLoggedIn = false,
                authRequested = false
            )
        )

        appStateStore.authState.updateEmail("")
        appStateStore.authState.updateProfile("")
        appStateStore.authState.setCredentialsValid(false)
        appStateStore.authState.setEmailFieldValid(false)
        appStateStore.authState.setEmailCodeValid(false)
        appStateStore.authState.setPasswordValid(false)
        appStateStore.authState.setPostRegLoginError(false)
    }

}