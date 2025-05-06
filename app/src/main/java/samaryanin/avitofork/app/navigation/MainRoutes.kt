package samaryanin.avitofork.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainRoutes(val route: String) {

    /**
     * Стартовое окно при запуске приложения (под BottomNavigation)
     */
    @Serializable object MainScreen : MainRoutes("main")

    /**
     * Идентификатор дополненной навигации (над BottomNavigation)
     */
    @Serializable object UtilRouteID : MainRoutes("utils_id")

}










