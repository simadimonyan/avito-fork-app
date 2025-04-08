package samaryanin.avitofork.domain.usecase.auth

import android.util.Log
import androidx.compose.runtime.Immutable
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.dto.auth.session.LoginRequest
import samaryanin.avitofork.data.network.dto.auth.session.SessionResponse
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.repository.NetworkRepository
import samaryanin.avitofork.domain.state.DomainStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class LoginUseCase @Inject constructor(
    private val repository: NetworkRepository,
    private val state: DomainStateStore
) {

    private val authService = repository.authServiceRepository

    suspend fun login(email: String, password: String): AuthStatus {

        try {
            when (val result = authService.login(LoginRequest(email, password))) {

                is Result.Success -> {

                    if (result.data is SessionResponse.LoginResponse) {
                        state.authTokenStateHolder.updateServiceToken(result.data.token)
                        return AuthStatus.LOGIN_SUCCEED
                    }
                    else if (result.data is SessionResponse.SessionErrorResponse) {
                        return if (result.data.message.contains("invalid password")) {
                            AuthStatus.CREDENTIALS_ERROR
                        } else if (result.data.message.contains("user not found")) {
                            AuthStatus.USER_NOT_FOUND_ERROR
                        } else {
                            AuthStatus.ERROR(result.data.message)
                        }
                    }

                }

                is Result.Error -> {
                    Log.w("LoginUseCase", result.exception)
                    return AuthStatus.NETWORK_ISSUES("${result.exception.message}")
                }

            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            return AuthStatus.ERROR("${e.message}")
        }

        return AuthStatus.ERROR("Unknown error!")
    }

}