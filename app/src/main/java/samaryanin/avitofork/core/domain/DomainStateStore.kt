package samaryanin.avitofork.core.domain

import androidx.compose.runtime.Stable
import samaryanin.avitofork.feature.auth.domain.models.AuthTokenStateHolder
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