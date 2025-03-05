package samaryanin.avitofork.presentation.screens.auth.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.usecase.AuthUseCase
import samaryanin.avitofork.presentation.state.AppStateStore
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val auth: AuthUseCase
) : ViewModel() {

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.CheckEmailFormValidation -> emailFieldFormVerify(event.email)
            is AuthEvent.UpdateEmailState -> appStateStore.authStateHolder.updateEmail(event.email)
            is AuthEvent.UpdateProfileState -> appStateStore.authStateHolder.updateProfile(event.profile)
            is AuthEvent.CheckEmailCodeValidation -> emailFieldCodeVerify(event.email, event.code)
            is AuthEvent.SendVerificationCode -> sendVerificationCode()
            is AuthEvent.VerifyAccountCredentials -> verifyCredentials(event.email, event.pass)
            is AuthEvent.CheckPasswordFormValidation -> isPasswordValid(event.password)
        }
    }

    private fun isPasswordValid(password: String) {
        val bool = password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!~%_*?&:,№\\\"\\[\\]{}\\-;^<>\\\\|.#()+=])[A-Za-zА-Яа-я\\d@\$~\\\"!%'’_*?&:№,\\[\\]{}\\-;^<>\\\\|.#()+=]{8,}\$".toRegex())
        appStateStore.authStateHolder.setPasswordValid(bool)
    }

    private fun verifyCredentials(email: String, pass: String) {
        viewModelScope.launch {
            val response = auth.loginUseCase.login(email, pass)
            val result = response is AuthStatus.LOGIN_SUCCEED
            appStateStore.authStateHolder.setCredentialsValid(result)
        }
    }

    // TODO(подключить бекенд сервис для получения кода)
    private fun sendVerificationCode() {}

    private fun emailFieldCodeVerify(email: String, code: String) {
        viewModelScope.launch {
            val response = auth.verificationUseCase.verification(email, code)
            val result = response is AuthStatus.EMAIL_VERIFIED
            appStateStore.authStateHolder.setEmailCodeValid(result)
        }
    }

    private fun emailFieldFormVerify(email: String) {
        val bool = email.matches("^[a-zA-Zа-яА-ЯёЁ0-9._%+-]+@[a-zA-Zа-яА-ЯёЁ0-9.-]+\\.[a-zA-Zа-яА-ЯёЁ]{2,}\$".toRegex())
        appStateStore.authStateHolder.setEmailFieldValid(bool)
    }

//    private fun hash(data: String): String {
//        val bytes = data.toByteArray()
//        val md = MessageDigest.getInstance("SHA-256")
//        val digest = md.digest(bytes)
//        return digest.fold("") { str, it -> str + "%02x".format(it) }
//    }

}