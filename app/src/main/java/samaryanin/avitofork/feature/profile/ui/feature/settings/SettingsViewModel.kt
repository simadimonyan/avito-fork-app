package samaryanin.avitofork.feature.profile.ui.feature.settings

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