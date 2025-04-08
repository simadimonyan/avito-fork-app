package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.marketplace.domain.model.post.CategoryField
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.CategoryScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.PostCreateScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.SubCategoryScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.navigation.data.CategoryNavType
import kotlin.reflect.typeOf

/**
 * Расширение утилитной навигации управления объявлениями выше слоя BottomNavigation
 * ---------------------------------------------------------------------------------
 * @param globalNavController глобальный контроллер навигации
 * @param profileViewModel модель профиля
 * @param categoriesViewModel модель категорий объявлений приложения
 */
fun NavGraphBuilder.utilPosterGraph(
    globalNavController: NavHostController,
    profileViewModel: ProfileViewModel,
    categoriesViewModel: CategoryViewModel
) {

    navigation(startDestination = PostRoutes.PostCategories.route, route = PostRoutes.RouteID.route) {

        composable( // Категории
            route = PostRoutes.PostCategories.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            }
        ) {
            CategoryScreen(globalNavController, categoriesViewModel)
        }

        composable<PostRoutes.PostSubCategories>( // Подкатегории
            typeMap = mapOf(
                typeOf<CategoryField.Category>() to CategoryNavType.CategoryType
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            }
        ) { backStackEntry ->

            val json = Json {
                classDiscriminator = "type"
                ignoreUnknownKeys = true // например, "type"
            }

            val category = backStackEntry.arguments?.getString("category")?.let {
                json.decodeFromString<CategoryField.Category>(it)
            } ?: CategoryField.Category("", "", emptyList())


            SubCategoryScreen(globalNavController, category, categoriesViewModel)
        }

        composable<PostRoutes.PostCreate>( // Создать объявление
            typeMap = mapOf(
                typeOf<CategoryField.SubCategory>() to CategoryNavType.CategoryType
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 250
                    )
                )
            }
        ) { backStackEntry ->

            val json = Json {
                classDiscriminator = "type"
                ignoreUnknownKeys = true // например, "type"
            }

            val subcategory = backStackEntry.arguments?.getString("subCategory")?.let {
                json.decodeFromString<CategoryField.SubCategory>(it)
            } ?: CategoryField.SubCategory("", "", emptyList())


            PostCreateScreen(globalNavController, subcategory, categoriesViewModel, profileViewModel)
        }

    }

}