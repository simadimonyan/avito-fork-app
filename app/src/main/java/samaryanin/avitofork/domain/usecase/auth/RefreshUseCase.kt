package samaryanin.avitofork.domain.usecase.auth

import android.util.Log
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.dto.auth.account.AccountResponse
import samaryanin.avitofork.domain.repository.Repository
import samaryanin.avitofork.data.network.dto.auth.account.RefreshRequest
import samaryanin.avitofork.domain.model.auth.AuthStatus
import samaryanin.avitofork.domain.state.DomainStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshUseCase @Inject constructor(
    private val repository: Repository,
    private val state: DomainStateStore
) {

    private val authService = repository.authServiceRepository
    private val serviceToken = state.authTokenStateHolder.authTokenState.value.serviceToken
    private val refreshToken = state.authTokenStateHolder.authTokenState.value.refreshToken

    suspend fun refresh() {

        try {
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