package samaryanin.avitofork.feature.feed.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class FeedRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : FeedRoutes("search_id")

    /**
     * Окно для поиска актуальных объявлений (под BottomNavigation)
     */
    @Serializable object Feed : FeedRoutes("search")

    /**
     * Окно карточки товара в поиске актуальных объявлений (над BottomNavigation)
     */
    @Serializable object AdditionalInfoScreen : FeedRoutes("additional_info_screen")

    /**
     * Окно корзины покупок (над BottomNavigation)
     */
    @Serializable object ShoppingCart : FeedRoutes("cart")

}