package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Stable
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Chat

@Stable
object ChatNavType {

    private  val json = Json {
        ignoreUnknownKeys = true
    }

    @Stable
    object ChatType : NavType<Chat>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): Chat? {
            val rawString = bundle.getString(key)
            return rawString?.let { json.decodeFromString<Chat>(it) }
        }

        override fun parseValue(value: String): Chat {
            return json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Chat): String {
            return Uri.encode(json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Chat) {
            val serialized = json.encodeToString(value)
            bundle.putString(key, serialized)
        }

    }

}