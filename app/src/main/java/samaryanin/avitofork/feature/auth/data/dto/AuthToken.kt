package samaryanin.avitofork.feature.auth.data.dto

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AuthToken(

    /**
     * Токен авторизации для входа в аккаунт
     */
    val accessToken: String = "",

    /**
     * Токен для обновления [accessToken]
     */
    val refreshToken: String = "",

)

