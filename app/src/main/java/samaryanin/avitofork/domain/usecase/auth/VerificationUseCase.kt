package samaryanin.avitofork.domain.usecase.auth

import android.util.Log
import androidx.compose.runtime.Immutable
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.dto.auth.session.SessionResponse
import samaryanin.avitofork.data.network.dto.auth.session.VerifyRequest
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.repository.NetworkRepository
import samaryanin.avitofork.domain.state.DomainStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class VerificationUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val state: DomainStateStore
) {

    private val authService = networkRepository.authServiceRepository

    suspend fun verification(email: String, code: String): AuthStatus {

        try {
            val serviceToken = state.authTokenStateHolder.authTokenState.value.serviceToken

            when (val result = authService.verify(serviceToken, VerifyRequest(code, email))) {

                is Result.Success -> {

                    if (result.data is SessionResponse.VerifyResponse) {
                        state.authTokenStateHolder.updateServiceToken("")
                        state.authTokenStateHolder.updateAccessToken(result.data.accessToken)
                        state.authTokenStateHolder.updateRefreshToken(result.data.refreshToken)
                        return AuthStatus.EMAIL_VERIFIED
                    }
                    else if (result.data is SessionResponse.SessionErrorResponse) {
                        return AuthStatus.CREDENTIALS_ERROR
                    }

                }

                is Result.Error -> {
                    Log.w("VerificationUseCase", result.exception)
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