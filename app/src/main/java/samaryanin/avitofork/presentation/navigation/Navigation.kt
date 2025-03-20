package samaryanin.avitofork.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.auth.navigation.authGraph
import samaryanin.avitofork.presentation.screens.menu.favorites.FavoritesScreen
import samaryanin.avitofork.presentation.screens.menu.favorites.navigation.FavoriteRoutes
import samaryanin.avitofork.presentation.screens.menu.messages.navigation.MessagesRoutes
import samaryanin.avitofork.presentation.screens.menu.profile.ProfileScreen
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.ProfileRoutes
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.MarketplaceScreen
import samaryanin.avitofork.presentation.screens.menu.search.navigation.SearchRoutes
import samaryanin.avitofork.presentation.screens.start.MainScreen
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
 * Главный Navigation Host Graph от [MainScreen]
 * ----------------------------------------------
 * @param mainViewModel главная модель приложения
 */
@Composable
fun GlobalGraph(mainViewModel: MainViewModel) {

    val globalNavController = rememberNavController()

    val authViewModel: AuthViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val categoriesViewModel: CategoryViewModel = hiltViewModel()

    NavHost(
        navController = globalNavController,
        startDestination = MainRoutes.MainScreen.route
    ) {

        composable(
            route = MainRoutes.MainScreen.route
        ) {
            MainScreen(
                mainViewModel,
                globalNavController
            )
        }

        utilGraph(
            mainViewModel,
            profileViewModel,
            categoriesViewModel,
            globalNavController
        )

        authGraph(
            authViewModel,
            mainViewModel,
            globalNavController
        )

    }

}

/**
 * Вложенный Navigation Host Graph экранов под слоем BottomSheet
 * ----------------------------------------------
 * @param screenNavController контроллер навигации между экранами
 * @param mainViewModel главная модель приложения
 * @param globalNavController глобальный контроллер навигации
 */
@Composable
fun NestedScreenGraph(
    screenNavController: NavHostController,
    mainViewModel: MainViewModel,
    globalNavController: NavHostController
) {

    val profileViewModel: ProfileViewModel = hiltViewModel()

    NavHost(
        navController = screenNavController,
        startDestination = SearchRoutes.Search.route
    ) {

        // ----------------------- Объявления -----------------------

        composable( // Объявления
            route = SearchRoutes.Search.route,
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
            MarketplaceScreen(globalNavController)
        }

        // ------------------------ Избранное ------------------------

        composable(
            route = FavoriteRoutes.Favorite.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            FavoritesScreen(
                // profileViewModel,
                mainViewModel,
                globalNavController
            )
        }

        // ------------------------ Сообщения ------------------------

        composable(
            route = MessagesRoutes.Messages.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            Box {} //TODO
        }

        // ------------------------- Профиль --------------------------

        composable(
            route = ProfileRoutes.Profile.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            ProfileScreen(profileViewModel, mainViewModel, globalNavController)
        }

        // -----------------------------------------------------------

    }

}



