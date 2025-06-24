package samaryanin.avitofork.feature.profile.ui.state.settings

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.app.activity.data.AppState
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.auth.ui.state.AuthStateHolder
import samaryanin.avitofork.feature.favorites.data.FavoriteManager
import javax.inject.Inject

@Stable
@HiltViewModel
class SettingsViewModel @Inject constructor(
    val appStateHolder: AppStateHolder,
    val authStateHolder: AuthStateHolder,
    private val favoriteManager: FavoriteManager,
    private val cacheManager: CacheManager,
) : ViewModel() {

    fun logout() {
        favoriteManager.clear()
        cacheManager.clearAuthToken()

        appStateHolder.updateState(
            AppState(
                isFirstStartUp = false,
                isLoggedIn = false,
                authRequested = false
            )
        )

        authStateHolder.updateEmail("")
        authStateHolder.updateProfile("")
        authStateHolder.setCredentialsValid(false)
        authStateHolder.setEmailFieldValid(false)
        authStateHolder.setEmailCodeValid(false)
        authStateHolder.setPasswordValid(false)
        authStateHolder.setPostRegLoginError(false)
    }

}