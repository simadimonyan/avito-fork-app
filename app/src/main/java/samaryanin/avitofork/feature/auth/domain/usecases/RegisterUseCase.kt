package samaryanin.avitofork.feature.auth.domain.usecases

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.auth.data.repository.AuthRepository
import samaryanin.avitofork.feature.auth.domain.models.AuthStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun login(email: String, password: String, name: String): AuthStatus {
        val bool = authRepository.register(email, password, name)

        if (bool) return AuthStatus.SIGNUP_SUCCEED
        return AuthStatus.USER_ALREADY_EXISTS_ERROR
    }

}