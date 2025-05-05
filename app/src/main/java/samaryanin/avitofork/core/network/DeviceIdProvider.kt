package samaryanin.avitofork.core.network

import android.content.Context
import androidx.core.content.edit
import java.util.UUID

object DeviceIdProvider {

    private const val PREF_NAME = "app_prefs"
    private const val DEVICE_ID_KEY = "device_id"

    fun getDeviceId(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var id = prefs.getString(DEVICE_ID_KEY, null)
        if (id == null) {
            id = UUID.randomUUID().toString()
            prefs.edit() { putString(DEVICE_ID_KEY, id) }
        }
        return id
    }
}