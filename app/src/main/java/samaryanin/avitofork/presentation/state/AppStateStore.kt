package samaryanin.avitofork.presentation.state

import samaryanin.avitofork.presentation.screens.auth.data.AuthStateHolder
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileStateHolder
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryStateHolder
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
    val appStateHolder: AppStateHolder = AppStateHolder()

    /**
     * Состояние авторизации
     */
    val authStateHolder: AuthStateHolder = AuthStateHolder()

    /**
     * Состояние данных профиля
     */
    val profileStateHolder: ProfileStateHolder = ProfileStateHolder()

    /**
     * Состояние категорий для управления объявлениями
     */
    val categoryStateHolder: CategoryStateHolder = CategoryStateHolder()

}