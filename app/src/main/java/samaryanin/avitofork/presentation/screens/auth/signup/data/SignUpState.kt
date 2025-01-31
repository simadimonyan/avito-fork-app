package samaryanin.avitofork.presentation.screens.auth.signup.data

data class SignUpState(

    val isEnabled: Boolean = false,
    val email: String = "",
    val code: String = "",

    /**
     * Состояние правильности валидации данных как электронную почту
     */
    var emailIsValid: Boolean = false

)