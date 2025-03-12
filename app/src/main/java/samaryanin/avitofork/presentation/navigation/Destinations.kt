package samaryanin.avitofork.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainRoutes(val route: String) {

    /**
     * Стартовое окно при запуске приложения (под BottomSheet)
     */
    @Serializable object MainScreen : MainRoutes("main")

    /**
     * Идентификатор дополненной навигации (над BottomSheet)
     */
    @Serializable object UtilRouteID : MainRoutes("utils_id")

}

@Serializable
sealed class SearchRoutes(val route: String) {

    /**
     * Окно для поиска актуальных объявлений (под BottomSheet)
     */
    @Serializable object Search : SearchRoutes("search")

    /**
     * Окно карточки товара в поиске актуальных объявлений (над BottomSheet)
     */
    @Serializable object AdditionalInfoScreen : SearchRoutes("additional_info_screen")

    /**
     * Окно корзины покупок (над BottomSheet)
     */
    @Serializable object ShoppingCart : SearchRoutes("cart")

}

/**
 * Ветка над BottomSheet
 */
@Serializable
sealed class GoodRoutes(val route: String) {

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
     * Меню профиля пользователя (под BottomSheet)
     */
    @Serializable object Profile : ProfileRoutes("profile")

    /**
     * Окно уведомлений (над BottomSheet)
     */
    @Serializable object Notifications : ProfileRoutes("notifications")

}

@Serializable
sealed class FavoriteRoutes(val route: String) {

    /**
     * Избранные (под BottomSheet)
     */
    @Serializable object Favorite : FavoriteRoutes("favorite")

    /**
     * дополнителтное Окно
     */
  //  @Serializable object Notifications : ProfileRoutes("notifications")

}

@Serializable
sealed class MessagesRoutes(val route: String) {

    /**
     * Окно сообщений (под BottomSheet)
     */
    @Serializable object Messages : MessagesRoutes("messages")

}

/**
 * Ветка над BottomSheet
 */
@Serializable
sealed class SettingsRoutes(val route: String) {

    /**
     * Меню профиля пользователя
     */
    @Serializable object Settings : SettingsRoutes("settings")

}


/**
 * Ветка над BottomSheet
 */
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





