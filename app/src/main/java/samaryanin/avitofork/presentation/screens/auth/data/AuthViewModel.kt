package samaryanin.avitofork.presentation.screens.auth.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.presentation.state.AppStateStore
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val appStateStore: AppStateStore
) : ViewModel() {

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.CheckEmailFormValidation -> emailFieldFormVerify(event.email)
            is AuthEvent.UpdateEmailState -> appStateStore.authStateHolder.updateEmail(event.email)
            is AuthEvent.UpdateProfileState -> appStateStore.authStateHolder.updateProfile(event.profile)
            is AuthEvent.CheckEmailCodeValidation -> emailFieldCodeVerify(event.code)
            is AuthEvent.SendVerificationCode -> sendVerificationCode()
            is AuthEvent.VerifyAccountCredentials -> verifyCredentials(event.email, event.hash)
            is AuthEvent.CheckPasswordFormValidation -> isPasswordValid(event.password)
        }
    }

    private fun isPasswordValid(password: String) {
        val bool = password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!~%_*?&:,№\\\"\\[\\]{}\\-;^<>\\\\|.#()+=])[A-Za-zА-Яа-я\\d@\$~\\\"!%'’_*?&:№,\\[\\]{}\\-;^<>\\\\|.#()+=]{8,}\$".toRegex())
        appStateStore.authStateHolder.setPasswordValid(bool)
    }

    // TODO(подключить бекенд сервис для авторизации) --тестовый пароль 1111
    private fun verifyCredentials(email: String, pass: String) {
        val bool = hash("$email $pass") == hash("$email 1111")
        appStateStore.authStateHolder.setCredentialsValid(bool)
    }

    // TODO(подключить бекенд сервис для получения кода)
    private fun sendVerificationCode() {}

    // TODO(подключить бекенд сервис для проверки кода)
    private fun emailFieldCodeVerify(code: String) {
        val bool = code.matches("^\\d{4}\$".toRegex())
        appStateStore.authStateHolder.setEmailCodeValid(bool)
    }

    // TODO(подключить бекенд сервис для отправки кода)
    private fun emailFieldFormVerify(email: String) {
        val bool = email.matches("^[a-zA-Zа-яА-ЯёЁ0-9._%+-]+@[a-zA-Zа-яА-ЯёЁ0-9.-]+\\.[a-zA-Zа-яА-ЯёЁ]{2,}\$".toRegex())
        appStateStore.authStateHolder.setEmailFieldValid(bool)
    }

    private fun hash(data: String): String {
        val bytes = data.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}