package samaryanin.avitofork.feature.auth.domain.usecases

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.core.database.cache.CacheManager
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

        if (authToken != null) {
            cacheManager.saveAuthToken(authToken)
            return AuthStatus.TOKEN_REFRESH_SUCCEED
        }
        return AuthStatus.TOKEN_REFRESH_ERROR
    }

}