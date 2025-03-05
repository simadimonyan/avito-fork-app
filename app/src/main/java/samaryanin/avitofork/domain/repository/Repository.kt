package samaryanin.avitofork.domain.repository

import samaryanin.avitofork.data.network.repository.AuthServiceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Repository @Inject constructor(

    /**
     * Сервис авторизации аккаунтов
     */
    val authServiceRepository: AuthServiceRepository

)