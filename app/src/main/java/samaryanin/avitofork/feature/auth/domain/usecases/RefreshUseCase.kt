package samaryanin.avitofork.feature.auth.domain.usecases

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.auth.data.repository.AuthRepository
import samaryanin.avitofork.feature.auth.domain.models.AuthStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class RefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val cacheManager: CacheManager
) {

    suspend fun refresh(): AuthStatus {
        val oldToken = cacheManager.getAuthToken()

        val authToken = authRepository.refresh(oldToken.accessToken, oldToken.refreshToken)

        return if (authToken?.refreshToken?.isNotBlank() == true && authToken.accessToken.isNotBlank()) {
            cacheManager.saveAuthToken(authToken)
            AuthStatus.TOKEN_REFRESH_SUCCEED
        } else {
            AuthStatus.TOKEN_REFRESH_ERROR
        }
    }
}