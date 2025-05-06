package samaryanin.avitofork.shared.state

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.feature.auth.ui.state.AuthStateHolder
import samaryanin.avitofork.feature.messages.ui.state.MessageStateHolder
import samaryanin.avitofork.feature.poster.ui.state.CategoryStateHolder
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileStateHolder
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