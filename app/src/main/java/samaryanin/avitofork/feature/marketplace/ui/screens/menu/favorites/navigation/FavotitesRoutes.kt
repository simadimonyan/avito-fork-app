package samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class FavoriteRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : FavoriteRoutes("favorites_id")

    /**
     * Избранные (под BottomNavigation)
     */
    @Serializable object Favorite : FavoriteRoutes("favorite")

    /**
     * дополнителтное Окно
     */
    //  @Serializable object Notifications : ProfileRoutes("notifications")

}