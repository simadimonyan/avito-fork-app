package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.navigation

import kotlinx.serialization.Serializable

/**
 * Ветка над BottomNavigation
 */
@Serializable
sealed class PosterRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : PosterRoutes ("poster_id")

    /**
     * Меню выбора категории товара или услуги
     */
    @Serializable object PosterCategories : PosterRoutes("good_categories")

    /**
     * Экран создания объявления о товаре или услуге
     */
    @Serializable object PosterCreate : PosterRoutes("good_create")

}