package samaryanin.avitofork.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import samaryanin.avitofork.presentation.screens.start.data.AppState
import javax.inject.Inject

class CacheManager @Inject constructor(
    private val preferences: SharedPreferences
) {

    private val gson = Gson()

    // основная конфигурация состояния приложения
    fun getAppState(): AppState {
        val json =  preferences.getString("uiAppState", null)

        val type = object : TypeToken<AppState>() {}.type

        val state: AppState = try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            return AppState()
        }
        return state
    }

    fun saveAppState(appState: AppState) {
        val json = gson.toJson(appState)
        preferences.edit().putString("uiAppState", json).apply()
    }

}

