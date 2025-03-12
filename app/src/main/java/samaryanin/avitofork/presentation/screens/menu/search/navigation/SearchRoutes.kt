package samaryanin.avitofork.presentation.screens.menu.search.navigation

import kotlinx.serialization.Serializable
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.ProfileRoutes

@Serializable
sealed class SearchRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : SearchRoutes("search_id")

    /**
     * Окно для поиска актуальных объявлений (под BottomNavigation)
     */
    @Serializable object Search : SearchRoutes("search")

    /**
     * Окно карточки товара в поиске актуальных объявлений (над BottomNavigation)
     */
    @Serializable object AdditionalInfoScreen : SearchRoutes("additional_info_screen")

    /**
     * Окно корзины покупок (над BottomNavigation)
     */
    @Serializable object ShoppingCart : SearchRoutes("cart")

}