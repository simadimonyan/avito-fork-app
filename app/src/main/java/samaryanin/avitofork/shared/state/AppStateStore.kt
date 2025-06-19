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
class AppStateStore @Inject constructor(
    private val appStateHolder: AppStateHolder,
    private val authStateHolder: AuthStateHolder,
    private val messageStateHolder: MessageStateHolder,
    private val profileStateHolder: ProfileStateHolder,
    private val categoryStateHolder: CategoryStateHolder
) {

    /**
     * Общее состояние приложения
     */
    val appState: AppStateHolder = appStateHolder

    /**
     * Состояние авторизации
     */
    val authState: AuthStateHolder = authStateHolder

    /**
     * Состояние чатов сообщений
     */
    val messageState: MessageStateHolder = messageStateHolder

    /**
     * Состояние данных профиля
     */
    val profileState: ProfileStateHolder = profileStateHolder

    /**
     * Состояние категорий для управления объявлениями
     */
    val categoryState: CategoryStateHolder = categoryStateHolder

}