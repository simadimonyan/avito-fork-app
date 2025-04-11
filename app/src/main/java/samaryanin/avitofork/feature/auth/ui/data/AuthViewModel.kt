package samaryanin.avitofork.feature.auth.ui.data

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.domain.AuthUseCase
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.feature.auth.domain.models.AuthStatus
import javax.inject.Inject

@Stable
@HiltViewModel
class AuthViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val authUseCase: AuthUseCase
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
            is AuthEvent.RegisterAccount -> registerAccount(event.email, event.pass, event.name)
            is AuthEvent.Refresh -> refreshSession()
        }
    }

    private fun isPasswordValid(password: String) {
        val bool = password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!~%_*?&:,№\\\"\\[\\]{}\\-;^<>\\\\|.#()+=])[A-Za-zА-Яа-я\\d@\$~\\\"!%'’_*?&:№,\\[\\]{}\\-;^<>\\\\|.#()+=]{8,}\$".toRegex())
        appStateStore.authStateHolder.setPasswordValid(bool)
    }

    private fun verifyCredentials(email: String, pass: String) {
        viewModelScope.launch {
            appStateStore.authStateHolder.updateLoading(true)

            val response = authUseCase.loginUseCase.login(email, pass)
            val result = response is AuthStatus.LOGIN_SUCCEED
            appStateStore.authStateHolder.setCredentialsValid(result)
            if (result) appStateStore.appStateHolder.authorizeProfile()

            appStateStore.authStateHolder.updateLoading(false)
        }
    }

    private fun registerAccount(email: String, password: String, name: String) {
        viewModelScope.launch {
            appStateStore.authStateHolder.updateLoading(true)

            val regResponse = authUseCase.registerUseCase.register(email, password, name)
            val regResult = regResponse is AuthStatus.SIGNUP_SUCCEED
            appStateStore.authStateHolder.setCredentialsValid(regResult)

            if (regResult) {
                val logResponse = authUseCase.loginUseCase.login(email, password)
                val logResult = logResponse is AuthStatus.LOGIN_SUCCEED
                if (logResult) appStateStore.appStateHolder.authorizeProfile()
                    else appStateStore.authStateHolder.setPostRegLoginError(true)
            }

            appStateStore.authStateHolder.updateLoading(false)
        }
    }

    private fun refreshSession() {
        viewModelScope.launch {
            appStateStore.authStateHolder.updateLoading(true)

            authUseCase.refreshUseCase.refresh()

            appStateStore.authStateHolder.updateLoading(false)
        }
    }

    // TODO(подключить бекенд сервис для получения кода)
    private fun sendVerificationCode() {}

    private fun emailFieldCodeVerify(email: String, code: String) {
        viewModelScope.launch {
            appStateStore.authStateHolder.updateLoading(true)

            val response = authUseCase.verifyUseCase.verify(email, code)
            val result = response is AuthStatus.EMAIL_VERIFIED

            appStateStore.authStateHolder.setEmailCodeValid(result)

            appStateStore.authStateHolder.updateLoading(false)
        }
    }

    private fun emailFieldFormVerify(email: String) {
        val bool = email.matches("^[a-zA-Zа-яА-ЯёЁ0-9._%+-]+@[a-zA-Zа-яА-ЯёЁ0-9.-]+\\.[a-zA-Zа-яА-ЯёЁ]{2,}\$".toRegex())
        appStateStore.authStateHolder.setEmailFieldValid(bool)
    }

}