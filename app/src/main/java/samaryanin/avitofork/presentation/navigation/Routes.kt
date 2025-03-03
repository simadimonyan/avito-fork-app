package samaryanin.avitofork.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import samaryanin.avitofork.presentation.screens.auth.login.LoginScreen
import samaryanin.avitofork.presentation.screens.auth.signup.SignUpScreen
import samaryanin.avitofork.presentation.screens.auth.VerificationScreen
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.auth.signup.CreateProfileScreen
import samaryanin.avitofork.presentation.screens.menu.profile.ProfileScreen
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.presentation.screens.notifications.NotificationsScreen
import samaryanin.avitofork.presentation.screens.menu.search.MarketplaceScreen
import samaryanin.avitofork.presentation.screens.settings.SettingsScreen
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
 * Вложенный Navigation Host Graph экранов
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
        composable(
            route = SearchRoutes.Search.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            MarketplaceScreen()
        }
        composable(
            route = SearchRoutes.ShoppingCart.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            Box(modifier = Modifier.fillMaxWidth()) { //TODO (заглушка)
                Text(text = "")
            }
        }
        profileGraph(
            profileViewModel,
            mainViewModel,
            globalNavController
        )
    }
}

/**
 * Вложенный дополненный Navigation Graph
 * ----------------------------------------------
 * @param mainViewModel главная модель приложения
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilGraph(
    mainViewModel: MainViewModel,
    globalNavController: NavHostController
) {
    navigation(startDestination = ProfileRoutes.Notifications.route, route = MainRoutes.UtilRouteID.route) {
        composable(
            route = ProfileRoutes.Notifications.route,
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
            NotificationsScreen(globalNavController)
        }
        composable(
            route = SettingsRoutes.Settings.route,
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
            SettingsScreen(globalNavController)
        }
    }
}

/**
 * Вложенный Navigation Graph профиля
 * ----------------------------------------------
 * @param profileViewModel модель экрана профиля
 * @param mainViewModel главная модель приложения
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.profileGraph(
    profileViewModel: ProfileViewModel,
    mainViewModel: MainViewModel,
    globalNavController: NavHostController
) {
    navigation(startDestination = ProfileRoutes.Profile.route, route = ProfileRoutes.RouteID.route) {
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
    }
}

/**
 * Вложенный Navigation Graph авторизации
 * ----------------------------------------------
 * @param authViewModel модель экрана авторизации
 * @param mainViewModel главная модель приложения
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.authGraph(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    globalNavController: NavHostController,
) {
    navigation(startDestination = AuthRoutes.Login.route, route = AuthRoutes.RouteID.route) {
        composable(
            route = AuthRoutes.Login.route,
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
            LoginScreen(authViewModel, globalNavController)
        }
        composable(
            route = AuthRoutes.SignUp.route,
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
            SignUpScreen(authViewModel, globalNavController)
        }
        composable(
            route = AuthRoutes.Verification.route,
            arguments = listOf(
                navArgument("createProfile") {
                    type = NavType.BoolType
                }
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
            val createProfile = backStackEntry.arguments?.getBoolean("createProfile") ?: false
            VerificationScreen(authViewModel, mainViewModel, globalNavController, createProfile)
        }
        composable(
            route = AuthRoutes.CreateProfile.route,
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
            CreateProfileScreen(authViewModel, mainViewModel, globalNavController)
        }
    }
    
}


