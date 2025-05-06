package samaryanin.avitofork.feature.auth.domain.usecases

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.auth.data.repository.AuthRepository
import samaryanin.avitofork.feature.auth.domain.models.AuthStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class VerifyUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val cacheManager: CacheManager
) {

    suspend fun verify(email: String, code: String): AuthStatus {
        val authToken = authRepository.verify(email, code)

        if (authToken != null) {
            cacheManager.saveAuthToken(authToken)
            return AuthStatus.EMAIL_VERIFIED
        }
        return AuthStatus.CREDENTIALS_ERROR
    }

}