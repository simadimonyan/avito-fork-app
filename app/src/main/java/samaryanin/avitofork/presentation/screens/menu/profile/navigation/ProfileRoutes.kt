package samaryanin.avitofork.presentation.screens.menu.profile.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ProfileRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : ProfileRoutes("profile_id")

    /**
     * Меню профиля пользователя (под BottomNavigation)
     */
    @Serializable object Profile : ProfileRoutes("profile")

    /**
     * Окно уведомлений (над BottomNavigation)
     */
    @Serializable object Notifications : ProfileRoutes("notifications")

}