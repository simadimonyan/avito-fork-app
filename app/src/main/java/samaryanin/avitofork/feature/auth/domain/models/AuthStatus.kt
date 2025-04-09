package samaryanin.avitofork.feature.auth.domain.models

sealed class AuthStatus(val label: String) {

    data class NETWORK_ISSUES(val message: String) : AuthStatus("Network issues: $message")

    object LOGIN_SUCCEED : AuthStatus("Successfully logged in!")

    object SIGNUP_SUCCEED : AuthStatus("Successfully signed up!")

    object CREDENTIALS_ERROR : AuthStatus("Credentials are not correct!")

    object EMAIL_VERIFIED : AuthStatus("Email successfully verified!")

    object USER_NOT_FOUND_ERROR : AuthStatus("User is not found!")

    object USER_ALREADY_EXISTS_ERROR : AuthStatus("User already exists!")

    data class ERROR(val message: String) : AuthStatus("Unknown error: $message")

}