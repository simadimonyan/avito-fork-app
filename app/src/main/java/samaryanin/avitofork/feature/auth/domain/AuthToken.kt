package samaryanin.avitofork.feature.auth.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

data class AuthToken(

    /**
     * Токен аутентификации для определения в системе (действует 5 мин)
     */
    val serviceToken: String = "",

    /**
     * Токен авторизации для входа в аккаунт (действует 15 мин)
     */
    val accessToken: String = "",

    /**
     * Токен для обновления [accessToken] (действует 6 месяцев)
     */
    val refreshToken: String = "",

)

@Singleton
class AuthTokenStateHolder @Inject constructor() {

    private val _authTokenState = MutableStateFlow(AuthToken())
    val authTokenState: StateFlow<AuthToken> = _authTokenState

    fun updateServiceToken(token: String) {
        _authTokenState.update { it.copy(serviceToken = token) }
    }

    fun updateAccessToken(token: String) {
        _authTokenState.update { it.copy(accessToken = token) }
    }

    fun updateRefreshToken(token: String) {
        _authTokenState.update { it.copy(refreshToken = token) }
    }

}
