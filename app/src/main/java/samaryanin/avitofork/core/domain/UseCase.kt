package samaryanin.avitofork.core.domain

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.marketplace.domain.usecase.poster.ConfigurationUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Группа use case для авторизации
 */
//@Singleton
//@Immutable
//data class AuthUseCase @Inject constructor(
////    val signUpUseCase: SignUpUseCase,
////    val loginUseCase: LoginUseCase,
////    val refreshUseCase: RefreshUseCase,
////    val verificationUseCase: VerificationUseCase
//)

/**
 * Группа use case для управления объявлениями
 */
@Singleton
@Immutable
data class PostUseCase @Inject constructor(
    val configurationUseCase: ConfigurationUseCase
)