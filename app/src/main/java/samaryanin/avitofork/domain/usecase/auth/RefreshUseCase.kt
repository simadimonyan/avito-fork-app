package samaryanin.avitofork.domain.usecase.auth

import android.util.Log
import androidx.compose.runtime.Immutable
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.dto.auth.account.AccountResponse
import samaryanin.avitofork.domain.repository.Repository
import samaryanin.avitofork.data.network.dto.auth.account.RefreshRequest
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.state.DomainStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class RefreshUseCase @Inject constructor(
    private val repository: Repository,
    private val state: DomainStateStore
) {

    private val authService = repository.authServiceRepository

    suspend fun refresh() {

        try {
            val refreshToken = state.authTokenStateHolder.authTokenState.value.refreshToken
            val serviceToken = state.authTokenStateHolder.authTokenState.value.serviceToken

            when (val result = authService.refresh(serviceToken, RefreshRequest(refreshToken))) {

                is Result.Success -> {

                    if (result.data is AccountResponse.RefreshResponse) {

                        state.authTokenStateHolder.updateServiceToken("")
                        state.authTokenStateHolder.updateAccessToken(result.data.accessToken)
                        state.authTokenStateHolder.updateRefreshToken(result.data.refreshToken)

                    }

                }

                is Result.Error -> {
                    Log.w("RefreshUseCase", result.exception)
                }

            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

    }

}