package samaryanin.avitofork.core.ui.start.data.state

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.auth.ui.data.AuthStateHolder
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessageStateHolder
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data.ProfileStateHolder
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data.CategoryStateHolder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Глобальный репозиторий состояний приложения
 */
@Singleton
@Immutable
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
     * Состояние чатов сообщений
     */
    val messageStateHolder: MessageStateHolder = MessageStateHolder()

    /**
     * Состояние данных профиля
     */
    val profileStateHolder: ProfileStateHolder = ProfileStateHolder()

    /**
     * Состояние категорий для управления объявлениями
     */
    val categoryStateHolder: CategoryStateHolder = CategoryStateHolder()

}