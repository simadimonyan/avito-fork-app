package samaryanin.avitofork.presentation.screens.menu.profile.poster.navigation.data

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import samaryanin.avitofork.domain.model.post.CategoryField


object CategoryNavType {

    private  val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true // например, "type"
    }

    object CategoryType : NavType<CategoryField>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): CategoryField? {
            val rawString = bundle.getString(key)
            Log.d("TYPE","Retrieved from bundle: $rawString")
            return rawString?.let { json.decodeFromString<CategoryField>(it) }
        }

        override fun parseValue(value: String): CategoryField {
            return json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: CategoryField): String {
            return Uri.encode(json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: CategoryField) {
            val serialized = json.encodeToString(value)
            Log.d("TYPE","Saving to bundle: $serialized")
            bundle.putString(key, serialized)
        }

    }
}