package samaryanin.avitofork.core.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.core.ui.start.MainScreen
import samaryanin.avitofork.core.ui.start.data.MainViewModel
import samaryanin.avitofork.feature.auth.ui.data.AuthViewModel
import samaryanin.avitofork.feature.auth.ui.navigation.authGraph
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites.FavoritesScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites.navigation.FavoriteRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.MessagesScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.navigation.MessagesRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.ProfileScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.navigation.ProfileRoutes
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.marketplace.MarketplaceScreen
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.navigation.SearchRoutes

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
    val messagesViewModel: MessagesViewModel = hiltViewModel()

    NavHost(
        navController = globalNavController,
        startDestination = MainRoutes.MainScreen.route
    ) {

        composable(
            route = MainRoutes.MainScreen.route
        ) {
            MainScreen(
                mainViewModel,
                profileViewModel,
                messagesViewModel,
                globalNavController
            )
        }

        utilGraph(
            mainViewModel,
            messagesViewModel,
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
    profileViewModel: ProfileViewModel,
    messagesViewModel: MessagesViewModel,
    globalNavController: NavHostController
) {

    NavHost(
        navController = screenNavController,
        startDestination = SearchRoutes.Search.route
    ) {

        // ----------------------- Объявления -----------------------

        composable( // Объявления
            route = SearchRoutes.Search.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
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
            MessagesScreen(globalNavController, messagesViewModel)
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



