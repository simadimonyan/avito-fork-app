package samaryanin.avitofork.domain.state

import androidx.compose.runtime.Stable
import samaryanin.avitofork.domain.model.auth.AuthTokenStateHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Stable
class DomainStateStore @Inject constructor() {

    /**
     * Состояние токенов авторизации
     */
    @Inject lateinit var authTokenStateHolder: AuthTokenStateHolder


}