package samaryanin.avitofork.presentation.screens.auth.signup.data

sealed class SignUpEvent {

    /**
     * Валидация данных как электронную почту
     */
    data class CheckEmailFormValidation(val data: String) : SignUpEvent()

    /**
     * Обновление состояния
     */
    data class UpdateState(val state: SignUpState) : SignUpEvent()

}