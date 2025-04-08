package samaryanin.avitofork.feature.auth.ui.data

import androidx.compose.runtime.Immutable

sealed class AuthEvent {

    /**
     * Валидация данных как электронную почту
     */
    @Immutable
    data class CheckEmailFormValidation(val email: String) : AuthEvent()

    /**
     * Валидация пароля по форме сложности
     */
    @Immutable
    data class CheckPasswordFormValidation(val password: String) : AuthEvent()

    /**
     * Валидация кода подтверждения
     */
    @Immutable
    data class CheckEmailCodeValidation(val email: String, val code: String) : AuthEvent()

    /**
     * Проверка существования аккаунта и соответствия пароля
     */
    @Immutable
    data class VerifyAccountCredentials(val email: String, val pass: String) : AuthEvent()

    /**
     * Отправить код подтверждения
     */
    object SendVerificationCode : AuthEvent()

    /**
     * Обновление состояния почты
     */
    @Immutable
    data class UpdateEmailState(val email: String) : AuthEvent()

    /**
     * Обновление состояния имени профиля
     */
    @Immutable
    data class UpdateProfileState(val profile: String) : AuthEvent()

}