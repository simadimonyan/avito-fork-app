package samaryanin.avitofork.domain.usecase.auth

import android.util.Log
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.dto.auth.session.RegisterRequest
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.repository.Repository
import samaryanin.avitofork.domain.state.DomainStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val repository: Repository,
    private val state: DomainStateStore
) {

    private val authService = repository.authServiceRepository

    suspend fun signUp(email: String, password: String): AuthStatus {

        try {
            when (val result = authService.register(RegisterRequest(email, password))) {

                is Result.Success -> {
                    state.authTokenStateHolder.updateServiceToken(result.data.token)
                    return AuthStatus.SIGNUP_SUCCEED
                }

                is Result.Error -> {
                    Log.w("SignUpUseCase", result.exception)
                    return AuthStatus.NETWORK_ISSUES("${result.exception.message}")
                }

            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            return AuthStatus.ERROR("${e.message}")
        }

    }

}