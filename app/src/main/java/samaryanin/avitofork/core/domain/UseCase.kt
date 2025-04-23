package samaryanin.avitofork.core.domain

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.auth.domain.usecases.LoginUseCase
import samaryanin.avitofork.feature.auth.domain.usecases.RefreshUseCase
import samaryanin.avitofork.feature.auth.domain.usecases.RegisterUseCase
import samaryanin.avitofork.feature.auth.domain.usecases.VerifyUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.poster.ConfigurationUseCase
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.usecases.LoadChatsUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Группа use case для авторизации
 */
@Singleton
@Immutable
data class AuthUseCase @Inject constructor(
    val loginUseCase: LoginUseCase,
    val registerUseCase: RegisterUseCase,
    val verifyUseCase: VerifyUseCase,
    val refreshUseCase: RefreshUseCase
)

/**
 * Группа use case для сообщений
 */
@Singleton
@Immutable
data class MessagesUseCase @Inject constructor(
    val loadChatsUseCase: LoadChatsUseCase
)

/**
 * Группа use case для управления объявлениями
 */
@Singleton
@Immutable
data class PostUseCase @Inject constructor(
    val configurationUseCase: ConfigurationUseCase
)