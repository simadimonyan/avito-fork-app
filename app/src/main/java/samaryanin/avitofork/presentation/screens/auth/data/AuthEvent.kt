package samaryanin.avitofork.presentation.screens.auth.data

sealed class AuthEvent {

    /**
     * Валидация данных как электронную почту
     */
    data class CheckEmailFormValidation(val email: String) : AuthEvent()

    /**
     * Валидация пароля по форме сложности
     */
    data class CheckPasswordFormValidation(val password: String) : AuthEvent()

    /**
     * Валидация кода подтверждения
     */
    data class CheckEmailCodeValidation(val code: String) : AuthEvent()

    /**
     * Проверка существования аккаунта и соответствия пароля
     */
    data class VerifyAccountCredentials(val email: String, val hash: String) : AuthEvent()

    /**
     * Отправить код подтверждения
     */
    object SendVerificationCode : AuthEvent()

    /**
     * Обновление состояния почты
     */
    data class UpdateEmailState(val email: String) : AuthEvent()

    /**
     * Обновление состояния имени профиля
     */
    data class UpdateProfileState(val profile: String) : AuthEvent()

}