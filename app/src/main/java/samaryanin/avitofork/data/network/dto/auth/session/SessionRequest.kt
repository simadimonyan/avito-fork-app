package samaryanin.avitofork.data.network.dto.auth.session

/**
 * /login - формат тела запроса
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * /register - формат тела запроса
 */
data class RegisterRequest(
    val email: String,
    val password: String
)

/**
 * /verify - формат тела запроса
 */
data class VerifyRequest(
    val code: String,
    val email: String
)

