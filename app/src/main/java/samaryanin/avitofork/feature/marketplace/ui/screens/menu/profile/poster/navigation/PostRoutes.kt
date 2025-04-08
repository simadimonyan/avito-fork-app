package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.navigation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import samaryanin.avitofork.feature.marketplace.domain.model.post.CategoryField

/**
 * Ветка над BottomNavigation
 */
@Serializable
sealed class PostRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable
    object RouteID : PostRoutes ("poster_id")

    /**
     * Меню выбора категории товара или услуги
     */
    @Serializable
    object PostCategories : PostRoutes("good_categories")

    /**
     * Меню выбора подкатегории товара или услуги
     * @param category передача выбранной категории в SubCategoryScreen
     */
    @Serializable
    @Immutable
    data class PostSubCategories(val category: CategoryField.Category) : PostRoutes("good_subcategories")

    /**
     * Экран создания объявления о товаре или услуге
     * @param subCategory передача выбранной подкатегории в PostCreateScreen
     */
    @Serializable
    @Immutable
    data class PostCreate(val subCategory: CategoryField.SubCategory) : PostRoutes("good_create")

}