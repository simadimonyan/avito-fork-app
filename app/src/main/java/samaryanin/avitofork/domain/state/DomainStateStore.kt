package samaryanin.avitofork.domain.state

import samaryanin.avitofork.domain.model.auth.AuthTokenStateHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DomainStateStore @Inject constructor() {

    /**
     * Состояние токенов авторизации
     */
    @Inject lateinit var authTokenStateHolder: AuthTokenStateHolder


}