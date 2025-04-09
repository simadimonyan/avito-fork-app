package samaryanin.avitofork.feature.auth.data.dto

import androidx.compose.runtime.Immutable

@Immutable
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

