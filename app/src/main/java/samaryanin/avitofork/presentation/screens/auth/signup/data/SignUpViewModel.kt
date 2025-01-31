package samaryanin.avitofork.presentation.screens.auth.signup.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun handleEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.CheckEmailFormValidation -> emailFieldFormVerify(event.data)
            is SignUpEvent.UpdateState -> updateState(event.state)
        }
    }

    private fun updateState(state: SignUpState) {
        _state.update { state }
    }

    private fun emailFieldFormVerify(email: String) {
        val bool = email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$".toRegex())
        _state.value.emailIsValid = bool
    }

}