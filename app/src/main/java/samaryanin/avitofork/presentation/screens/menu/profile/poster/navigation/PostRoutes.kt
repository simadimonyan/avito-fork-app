package samaryanin.avitofork.presentation.screens.menu.profile.poster.navigation

import kotlinx.serialization.Serializable
import samaryanin.avitofork.domain.model.post.CategoryField

/**
 * Ветка над BottomNavigation
 */
@Serializable
sealed class PostRoutes(val route: String) {

    /**
     * Уникальный идентификатор ветки
     */
    @Serializable object RouteID : PostRoutes ("poster_id")

    /**
     * Меню выбора категории товара или услуги
     */
    @Serializable object PostCategories : PostRoutes("good_categories")

    /**
     * Меню выбора подкатегории товара или услуги
     * - /{sendCategory} передача выбранной категории в SubCategoryScreen
     */
//    @Serializable object PostSubCategories : PostRoutes("good_subcategories/{sendCategory}") {
//        fun sendCategory(category: CategoryField.Category): String {
//            return PostSubCategories.route.replace("{sendCategory}", category.toString())
//        }
//    }
    @Serializable data class PostSubCategories(val category: CategoryField.Category) : PostRoutes("good_subcategories") {
        companion object {
            const val route = "good_subcategories"
        }
    }

    /**
     * Экран создания объявления о товаре или услуге
     * - /{sendSubCategory} передача выбранной подкатегории в PostCreateScreen
     */
//    @Serializable
//
//    object PostCreate : PostRoutes("good_create/{sendSubCategory}") {
//        fun sendSubCategory(subCategory: CategoryField.SubCategory): String {
//            return PostCreate.route.replace("{sendSubCategory}", subCategory.toString())
//        }
//    }

    @Serializable data class PostCreate(val subCategory: CategoryField.SubCategory) : PostRoutes("good_create") {
        companion object {
            const val route = "good_create"
        }
    }

}