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

    @Serializable object MarketPlaceScreen : MainRoutes("market_place_screen")
    @Serializable object AdditionalInfoScreen : MainRoutes("additional_info_screen")


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
sealed class GoodRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : GoodRoutes("goods_id")

    /**
     * Меню выбора категории товара или услуги
     */
    @Serializable object GoodCategories : GoodRoutes("good_categories")

    /**
     * Экран создания объявления о товаре или услуге
     */
    @Serializable object GoodCreate : GoodRoutes("good_create")

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
sealed class FavoriteRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : FavoriteRoutes("favorite_id")

    /**
     * Избранные
     */
    @Serializable object Favorite : FavoriteRoutes("favorite")

    /**
     * дополнителтное Окно
     */
  //  @Serializable object Notifications : ProfileRoutes("notifications")

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
    @Serializable object Verification : AuthRoutes("verification/{createProfile}") {
        fun createRoute(createProfile: Boolean): String {
            return route.replace("{createProfile}", createProfile.toString())
        }
    }

    /**
     * Окно для создания профиля
     */
    @Serializable object CreateProfile : AuthRoutes("create_profile")

}





