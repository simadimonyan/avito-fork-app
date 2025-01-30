package samaryanin.avitofork.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import samaryanin.avitofork.presentation.screens.start.state.UIAppState
import javax.inject.Inject

class CacheManager @Inject constructor(
    private val preferences: SharedPreferences
) {

    private val gson = Gson()

    // основная конфигурация состояния приложения
    fun getAppState(): UIAppState {
        val json =  preferences.getString("uiAppState", null)

        val type = object : TypeToken<UIAppState>() {}.type

        val state: UIAppState = try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            return UIAppState()
        }
        return state
    }

    fun saveAppState(appState: UIAppState) {
        val json = gson.toJson(appState)
        preferences.edit().putString("uiAppState", json).apply()
    }

}

