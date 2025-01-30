package samaryanin.avitofork.presentation.screens.auth.login.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginDataState())
    val state: StateFlow<LoginDataState> = _state

    fun updateLoginData(emailOrPhone: String?, password: String?) {
        updateState {
            LoginDataState(
                email = emailOrPhone,
                password = password
            )
        }
    }

    private fun updateState(update: LoginDataState.() -> LoginDataState) {
        _state.value = _state.value.update()
    }

    data class LoginDataState(
        val email: String? = "",
        val password: String? = ""
    )
}