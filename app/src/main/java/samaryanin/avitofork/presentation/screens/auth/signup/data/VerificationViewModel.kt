package samaryanin.avitofork.presentation.screens.auth.signup.data

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(PhoneVerificationState())
    val state: StateFlow<PhoneVerificationState> = _state

    fun updatePhone(phone: String?) {
        updateState {
            PhoneVerificationState(
                isEnabled = if (phone?.length!! < 9) false else true,
                phone = phone
            )
        }

        Log.d("PHONE", "${state.value.phone}")
    }

    fun updateCode(code: String?) {
        updateState {
            PhoneVerificationState(
                code = code
            )
        }

        Log.d("PHONE", "${state.value.phone}")
    }


    private fun updateState(update: PhoneVerificationState.() -> PhoneVerificationState) {
        _state.value = _state.value.update()
    }

    data class PhoneVerificationState(
        val isEnabled: Boolean = false,
        val phone: String? = "",
        val code: String? = ""
    )
}