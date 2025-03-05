package samaryanin.avitofork.domain.usecase.auth

import android.util.Log
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.dto.auth.session.LoginRequest
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.repository.Repository
import samaryanin.avitofork.domain.state.DomainStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val repository: Repository,
    private val state: DomainStateStore
) {

    private val authService = repository.authServiceRepository

    suspend fun login(email: String, password: String): AuthStatus {

        try {
            when (val result = authService.login(LoginRequest(email, password))) {

                is Result.Success -> {
                    state.authTokenStateHolder.updateServiceToken(result.data.token)
                    return AuthStatus.LOGIN_SUCCEED
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

    }

}