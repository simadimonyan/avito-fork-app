package samaryanin.avitofork.domain.model.auth

sealed class AuthStatus(val label: String) {

    data class NETWORK_ISSUES(val message: String) : AuthStatus("Network issues: $message")

    object LOGIN_SUCCEED : AuthStatus("Successfully logged in!")

    object SIGNUP_SUCCEED : AuthStatus("Successfully signed up!")

    object CREDENTIALS_ERROR : AuthStatus("Credentials are not correct!")

    object USER_ERROR : AuthStatus("User is not found!")

    data class ERROR(val message: String) : AuthStatus("Unknown error: $message")

}