package samaryanin.avitofork.presentation.screens.auth.data

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AuthUpState())
    val state: StateFlow<AuthUpState> = _state

    fun handleEvent(event: AuthUpEvent) {
        when (event) {
            is AuthUpEvent.CheckEmailFormValidation -> emailFieldFormVerify(event.email)
            is AuthUpEvent.UpdateState -> updateState(event.state)
            is AuthUpEvent.CheckEmailCodeValidation -> emailFieldCodeVerify(event.code)
            is AuthUpEvent.SendVerificationCode -> sendVerificationCode()
            is AuthUpEvent.VerifyAccountCredentials -> verifyCredentials(event.email, event.hash)
            is AuthUpEvent.CheckPasswordFormValidation -> isPasswordValid(event.password)
        }
    }

    private fun updateState(state: AuthUpState) {
        _state.update { state }
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!~%_*?&:,№\\\"\\[\\]{}\\-;^<>\\\\|.#()+=])[A-Za-zА-Яа-я\\d@\$~\\\"!%'’_*?&:№,\\[\\]{}\\-;^<>\\\\|.#()+=]{8,}\$".toRegex()
        Log.i("test", "password: ${password.matches(regex)}")
        return password.matches(regex)
    }

    // TODO(подключить бекенд сервис для авторизации) --тестовый пароль 1111
    private fun verifyCredentials(email: String, pass: String) {
        val bool = hash("$email $pass") == hash("$email 1111")
        _state.value.credentialsAreValid = bool
    }

    // TODO(подключить бекенд сервис для получения кода)
    private fun sendVerificationCode() {}

    // TODO(подключить бекенд сервис для проверки кода)
    private fun emailFieldCodeVerify(code: String) {
        val bool = code.matches("^\\d{4}\$".toRegex())
        _state.value.emailCodeIsValid = bool
    }

    // TODO(подключить бекенд сервис для отправки кода)
    private fun emailFieldFormVerify(email: String) {
        val bool = email.matches("^[a-zA-Zа-яА-ЯёЁ0-9._%+-]+@[a-zA-Zа-яА-ЯёЁ0-9.-]+\\.[a-zA-Zа-яА-ЯёЁ]{2,}\$".toRegex())
        _state.value.emailIsValid = bool
    }

    private fun hash(data: String): String {
        val bytes = data.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}