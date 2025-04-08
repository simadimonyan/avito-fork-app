package samaryanin.avitofork.feature.auth.domain

import androidx.compose.runtime.Stable
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