package samaryanin.avitofork.domain.usecase

import samaryanin.avitofork.domain.usecase.auth.LoginUseCase
import samaryanin.avitofork.domain.usecase.auth.RefreshUseCase
import samaryanin.avitofork.domain.usecase.auth.SignUpUseCase
import samaryanin.avitofork.domain.usecase.auth.VerificationUseCase
import samaryanin.avitofork.domain.usecase.posts.ConfigurationUseCase
import javax.inject.Inject

/**
 * Группа use case для авторизации
 */
data class AuthUseCase @Inject constructor(
    val signUpUseCase: SignUpUseCase,
    val loginUseCase: LoginUseCase,
    val refreshUseCase: RefreshUseCase,
    val verificationUseCase: VerificationUseCase
)

/**
 * Группа use case для управления объявлениями
 */
data class PostUseCase @Inject constructor(
    val configurationUseCase: ConfigurationUseCase
)