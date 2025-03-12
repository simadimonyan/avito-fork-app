package samaryanin.avitofork.presentation.screens.settings.navigation

import kotlinx.serialization.Serializable
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.ProfileRoutes

/**
 * Ветка над BottomNavigation
 */
@Serializable
sealed class SettingsRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : SettingsRoutes("settings_id")

    /**
     * Меню профиля пользователя
     */
    @Serializable object Settings : SettingsRoutes("settings")

}