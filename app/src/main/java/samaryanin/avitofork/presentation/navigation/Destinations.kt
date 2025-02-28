package samaryanin.avitofork.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainRoutes(val route: String) {

    /**
     * Стартовое окно при запуске приложения
     */
    @Serializable object MainScreen : MainRoutes("main")

    /**
     * Идентификатор дополненной навигации
     */
    @Serializable object UtilRouteID : MainRoutes("utils_id")

    @Serializable object GoodScreen : MainRoutes("good_screen")


}

@Serializable
sealed class SearchRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : SearchRoutes("search_id")

    /**
     * Меню для поиска актуальных объявлений
     */
    @Serializable object Search : SearchRoutes("search")

    /**
     * Меню корзины покупок
     */
    @Serializable object ShoppingCart : SearchRoutes("cart")

}

@Serializable
sealed class ProfileRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : ProfileRoutes("profile_id")

    /**
     * Меню профиля пользователя
     */
    @Serializable object Profile : ProfileRoutes("profile")

    /**
     * Окно уведомлений
     */
    @Serializable object Notifications : ProfileRoutes("notifications")

}

@Serializable
sealed class SettingsRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : SettingsRoutes("settings_id")

    /**
     * Меню профиля пользователя
     */
    @Serializable object Settings : SettingsRoutes("settings")

}

@Serializable
sealed class AuthRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : AuthRoutes("auth_id")

    /**
     * Окно для входа по номеру или почте
     */
    @Serializable object Login : AuthRoutes("login")

    /**
     * Окно для регистрации
     */
    @Serializable object SignUp : AuthRoutes("signup")

    /**
     * Окно для верификации кода
     * - /{createProfile} условие навигации окна создания профиля
     */
    @Serializable object Verification : AuthRoutes("verification/{createProfile}")

    /**
     * Окно для создания профиля
     */
    @Serializable object CreateProfile : AuthRoutes("create_profile")

}





