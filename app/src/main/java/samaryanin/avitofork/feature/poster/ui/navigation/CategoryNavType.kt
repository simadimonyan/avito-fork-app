package samaryanin.avitofork.feature.poster.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Stable
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.poster.domain.models.CategoryField

@Stable
object CategoryNavType {

    private  val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true // например, "type"
    }

    @Stable
    object CategoryType : NavType<CategoryField>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): CategoryField? {
            val rawString = bundle.getString(key)
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
            bundle.putString(key, serialized)
        }

    }

}