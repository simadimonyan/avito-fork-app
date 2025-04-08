package samaryanin.avitofork.domain.usecase

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.domain.usecase.auth.LoginUseCase
import samaryanin.avitofork.domain.usecase.auth.RefreshUseCase
import samaryanin.avitofork.domain.usecase.auth.SignUpUseCase
import samaryanin.avitofork.domain.usecase.auth.VerificationUseCase
import samaryanin.avitofork.domain.usecase.poster.ConfigurationUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Группа use case для авторизации
 */
@Singleton
@Immutable
data class AuthUseCase @Inject constructor(
    val signUpUseCase: SignUpUseCase,
    val loginUseCase: LoginUseCase,
    val refreshUseCase: RefreshUseCase,
    val verificationUseCase: VerificationUseCase
)

/**
 * Группа use case для управления объявлениями
 */
@Singleton
@Immutable
data class PostUseCase @Inject constructor(
    val configurationUseCase: ConfigurationUseCase
)