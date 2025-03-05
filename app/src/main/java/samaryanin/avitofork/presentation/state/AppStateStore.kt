package samaryanin.avitofork.presentation.state

import samaryanin.avitofork.presentation.screens.auth.data.AuthStateHolder
import samaryanin.avitofork.presentation.screens.start.data.AppStateHolder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Глобальный репозиторий состояний приложения
 */
@Singleton
class AppStateStore @Inject constructor() {

    /**
     * Общее состояние приложения
     */
    var appStateHolder: AppStateHolder = AppStateHolder()

    /**
     * Состояние авторизации
     */
     var authStateHolder: AuthStateHolder = AuthStateHolder()

}