package samaryanin.avitofork.feature.auth.ui.state

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.auth.domain.models.AuthStatus
import samaryanin.avitofork.feature.auth.domain.usecases.LoginUseCase
import samaryanin.avitofork.feature.auth.domain.usecases.RefreshUseCase
import samaryanin.avitofork.feature.auth.domain.usecases.RegisterUseCase
import samaryanin.avitofork.feature.auth.domain.usecases.VerifyUseCase
import samaryanin.avitofork.shared.state.AppStateStore
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import javax.inject.Inject

@Stable
@HiltViewModel
class AuthViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val refreshUseCase: RefreshUseCase,
    private val verifyUseCase: VerifyUseCase
) : ViewModel() {

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.CheckEmailFormValidation -> emailFieldFormVerify(event.email)
            is AuthEvent.UpdateEmailState -> appStateStore.authState.updateEmail(event.email)
            is AuthEvent.UpdateProfileState -> appStateStore.authState.updateProfile(event.profile)
            is AuthEvent.CheckEmailCodeValidation -> emailFieldCodeVerify(event.email, event.code) //, event.password
            is AuthEvent.SendVerificationCode -> sendVerificationCode()
            is AuthEvent.VerifyAccountCredentials -> verifyCredentials(event.email, event.pass)
            is AuthEvent.CheckPasswordFormValidation -> isPasswordValid(event.password)
            is AuthEvent.RegisterAccount -> registerAccount(event.email, event.pass, event.name)
            is AuthEvent.Refresh -> refreshSession()
        }
    }

    private fun isPasswordValid(password: String) {
        val bool = password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!~%_*?&:,№\\\"\\[\\]{}\\-;^<>\\\\|.#()+=])[A-Za-zА-Яа-я\\d@\$~\\\"!%'’_*?&:№,\\[\\]{}\\-;^<>\\\\|.#()+=]{8,}\$".toRegex())
        appStateStore.authState.setPasswordValid(bool)
    }

    private fun verifyCredentials(email: String, pass: String) {
        safeScope.launch {
            appStateStore.authState.updateLoading(true)

            val response = loginUseCase.login(email, pass)
            val result = response is AuthStatus.LOGIN_SUCCEED
            appStateStore.authState.setCredentialsValid(result)

            if (result) { appStateStore.appState.authorizeProfile() }

            appStateStore.authState.updateLoading(false)
        }
    }

    private fun registerAccount(email: String, password: String, name: String) {
        safeScope.launch {
            appStateStore.authState.updateLoading(true)

            val regResponse = registerUseCase.register(email, password, name)
            val regResult = regResponse is AuthStatus.SIGNUP_SUCCEED
            appStateStore.authState.setCredentialsValid(regResult)

            appStateStore.authState.updateLoading(false)
        }
    }

    private fun refreshSession() {
        safeScope.launch {
            appStateStore.authState.updateLoading(true)

            refreshUseCase.refresh()

            appStateStore.authState.updateLoading(false)
        }
    }

    // TODO(подключить бекенд сервис для получения кода)
    private fun sendVerificationCode() {}

    private fun emailFieldCodeVerify(email: String, code: String) { // , password: String
        safeScope.launch {
            appStateStore.authState.updateLoading(true)

            val response = verifyUseCase.verify(email, code)
            val result = response is AuthStatus.EMAIL_VERIFIED
            if (result) appStateStore.appState.authorizeProfile()

            appStateStore.authState.setEmailCodeValid(result)

//            if (result) {
//                val logResponse = authUseCase.loginUseCase.login(email, password)
//                val logResult = logResponse is AuthStatus.LOGIN_SUCCEED
//                if (logResult) {
//                    appStateStore.appStateHolder.authorizeProfile()
//                    appStateStore.authStateHolder.setPassword("cleared")
//                }
//                else appStateStore.authStateHolder.setPostRegLoginError(true)
//            }

            appStateStore.authState.updateLoading(false)
        }
    }

    private fun emailFieldFormVerify(email: String) {
        val bool = email.matches("^[a-zA-Zа-яА-ЯёЁ0-9._%+-]+@[a-zA-Zа-яА-ЯёЁ0-9.-]+\\.[a-zA-Zа-яА-ЯёЁ]{2,}\$".toRegex())
        appStateStore.authState.setEmailFieldValid(bool)
    }

}