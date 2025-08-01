package samaryanin.avitofork.feature.profile.ui.navigation.settings

import kotlinx.serialization.Serializable

/**
 * Ветка над BottomNavigation
 */
@Serializable
sealed class SettingsRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable
    object RouteID : SettingsRoutes("settings_id")

    /**
     * Меню профиля пользователя
     */
    @Serializable
    object Settings : SettingsRoutes("settings")

}