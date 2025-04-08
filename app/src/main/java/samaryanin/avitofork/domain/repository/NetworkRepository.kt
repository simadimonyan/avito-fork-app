package samaryanin.avitofork.domain.repository

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.data.repository.network.AuthServiceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
data class NetworkRepository @Inject constructor(

    /**
     * Сервис авторизации аккаунтов
     */
    val authServiceRepository: AuthServiceRepository

)