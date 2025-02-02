package samaryanin.avitofork.presentation.screens.auth.data

data class AuthUpState(

    /**
     * Персональные данные от аккаунта
     */
    val profile: String = "",
    val email: String = "",
    //val password: String? = "", //хеш
    val code: String = "",

    /**
     * Проверка существования аккаунта и соответствия пароля
     */
    var credentialsAreValid: Boolean = false,

    /**
     * Состояние валидации данных как электронную почту
     */
    var emailIsValid: Boolean = false,

    /**
     * Состояние валидации кода подтверждения
     */
    var emailCodeIsValid: Boolean = false,

    /**
     * Состояние валидации сложности пароля
     */
    var passwordFormIsValid: Boolean = false

)