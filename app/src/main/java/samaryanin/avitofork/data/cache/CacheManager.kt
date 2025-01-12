package samaryanin.avitofork.data.cache

import android.content.SharedPreferences
import javax.inject.Inject

class CacheManager @Inject constructor(
    private val preferences: SharedPreferences
) {

    fun isFirstStartup(): Boolean {
        return preferences.getBoolean("first_startup", true)
    }

    fun setFirstStartup(isFirst: Boolean) {
        preferences.edit().putBoolean("first_startup", isFirst).apply()
    }

}

