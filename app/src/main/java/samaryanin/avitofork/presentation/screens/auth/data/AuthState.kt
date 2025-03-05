package samaryanin.avitofork.presentation.screens.auth.data

import android.util.Log
import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Stable
data class AuthState(

    /**
     * Персональные данные от аккаунта
     */
    val profile: String = "",
    val email: String = "",

    /**
     * Проверка существования аккаунта и соответствия пароля
     */
    val credentialsAreValid: Boolean = false,

    /**
     * Состояние валидации данных как электронную почту
     */
    val emailIsValid: Boolean = false,

    /**
     * Состояние валидации кода подтверждения
     */
    val emailCodeIsValid: Boolean = false,

    /**
     * Состояние валидации сложности пароля
     */
    val passwordFormIsValid: Boolean = false

)

/**
 * State Holder паттерн
 */
@Singleton
class AuthStateHolder @Inject constructor() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun updateEmail(email: String) {
        _authState.update {
            it.copy(email = email)
        }
    }

    fun updateProfile(profile: String) {
        _authState.update { it.copy(profile = profile) }
    }

    fun setPasswordValid(isValid: Boolean) {
        _authState.update { it.copy(passwordFormIsValid = isValid) }
    }

    fun setCredentialsValid(isValid: Boolean) {
        _authState.update { it.copy(credentialsAreValid = isValid) }
    }

    fun setEmailCodeValid(isValid: Boolean) {
        _authState.update { it.copy(emailCodeIsValid = isValid) }
    }

    fun setEmailFieldValid(isValid: Boolean) {
        _authState.update { it.copy(emailIsValid = isValid) }
    }
}