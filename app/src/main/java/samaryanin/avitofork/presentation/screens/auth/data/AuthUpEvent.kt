package samaryanin.avitofork.presentation.screens.auth.data

sealed class AuthUpEvent {

    /**
     * Валидация данных как электронную почту
     */
    data class CheckEmailFormValidation(val data: String) : AuthUpEvent()

    /**
     * Валидация кода подтверждения
     */
    data class CheckEmailCodeValidation(val code: String) : AuthUpEvent()

    /**
     * Проверка существования аккаунта и соответствия пароля
     */
    data class VerifyAccountCredentials(val email: String, val hash: String) : AuthUpEvent()

    /**
     * Отправить код подтверждения
     */
    object SendVerificationCode : AuthUpEvent()

    /**
     * Обновление состояния
     */
    data class UpdateState(val state: AuthUpState) : AuthUpEvent()

}