package samaryanin.avitofork.feature.poster.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.feed.ui.feature.map.ui.MapScreen
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.ui.feature.poster.CategoryScreen
import samaryanin.avitofork.feature.poster.ui.feature.poster.PostCreateScreen
import samaryanin.avitofork.feature.poster.ui.feature.location.LocationScreen
import samaryanin.avitofork.feature.poster.ui.feature.poster.SubCategoryScreen
import samaryanin.avitofork.feature.poster.ui.state.CategoryViewModel
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel
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
            } ?: CategoryField.Category("", "", "", "", "", mutableListOf())


            SubCategoryScreen(globalNavController, category, categoriesViewModel)
        }


        composable( // карта
            route = PostRoutes.Map.route,
            arguments = listOf(
                navArgument("lat") { type = NavType.StringType },
                navArgument("lon") { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 250)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 250)
                )
            }
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
            val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull() ?: 0.0

            MapScreen(lat = lat, lon = lon)
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
            } ?: CategoryField.SubCategory("", "", "", "", "", emptyList())


            PostCreateScreen(globalNavController, subcategory, categoriesViewModel, profileViewModel)
        }

        composable<PostRoutes.LocationScreen>(
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
            LocationScreen(globalNavController, categoriesViewModel)
        }

    }

}