package samaryanin.avitofork.app.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.app.activity.ui.MainScreen
import samaryanin.avitofork.feature.auth.ui.navigation.authGraph
import samaryanin.avitofork.feature.auth.ui.state.AuthViewModel
import samaryanin.avitofork.feature.favorites.ui.FavoritesScreen
import samaryanin.avitofork.feature.favorites.ui.navigation.FavoriteRoutes
import samaryanin.avitofork.feature.feed.ui.feature.feed.MarketplaceScreen
import samaryanin.avitofork.feature.feed.ui.navigation.FeedRoutes
import samaryanin.avitofork.feature.messages.ui.feature.messages.MessagesScreen
import samaryanin.avitofork.feature.messages.ui.navigation.MessagesRoutes
import samaryanin.avitofork.feature.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.poster.ui.state.CategoryViewModel
import samaryanin.avitofork.feature.profile.ui.feature.profile.ProfileScreen
import samaryanin.avitofork.feature.profile.ui.navigation.profile.ProfileRoutes
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel

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
        startDestination = FeedRoutes.Feed.route
    ) {

        // ----------------------- Объявления -----------------------

        composable( // Объявления
            route = FeedRoutes.Feed.route,
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


        // ----------------------- Карта -----------------------

        composable( // Карта
            route = FeedRoutes.Feed.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            MarketplaceScreen(globalNavController)
        }
    }

}



