package samaryanin.avitofork.core.cache

import android.content.SharedPreferences
import androidx.compose.runtime.Immutable
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import samaryanin.avitofork.app.activity.data.AppState
import samaryanin.avitofork.feature.auth.data.dto.AuthToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class CacheManager @Inject constructor(
    val preferences: SharedPreferences
) {

    private val gson = Gson()

    // основная конфигурация состояния приложения
    fun getAppState(): AppState {
        val json =  preferences.getString("uiAppState", null)

        val type = object : TypeToken<AppState>() {}.type

        if (json != null) {
            val state: AppState = try {
                gson.fromJson(json, type)
            } catch (e: Exception) {
                e.printStackTrace()
                return AppState()
            }
            return state
        }
        else
            return AppState()
    }

    fun saveAppState(appState: AppState) {
        val json = gson.toJson(appState)
        preferences.edit { putString("uiAppState", json) }
    }

    fun getAuthToken(): AuthToken {
        val json =  preferences.getString("authToken", null)

        val type = object : TypeToken<AuthToken>() {}.type

        if (json != null) {
            val state: AuthToken = try {
                gson.fromJson(json, type)
            } catch (e: Exception) {
                e.printStackTrace()
                return AuthToken()
            }
            return state
        }
        else
            return AuthToken()
    }

    fun clearAuthToken() {
        preferences.edit { remove("authToken") }
    }

    fun saveAuthToken(authToken: AuthToken) {
        val json = gson.toJson(authToken)
        preferences.edit { putString("authToken", json) }
    }

}