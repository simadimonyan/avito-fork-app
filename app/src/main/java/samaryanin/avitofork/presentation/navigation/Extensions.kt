package samaryanin.avitofork.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import samaryanin.avitofork.presentation.screens.auth.VerificationScreen
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.auth.login.LoginScreen
import samaryanin.avitofork.presentation.screens.auth.signup.CreateProfileScreen
import samaryanin.avitofork.presentation.screens.auth.signup.SignUpScreen
import samaryanin.avitofork.presentation.screens.menu.search.poster_additional_info.AdditionalInfoScreen
import samaryanin.avitofork.presentation.screens.notifications.NotificationsScreen
import samaryanin.avitofork.presentation.screens.settings.SettingsScreen
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
 * Расширение утилитной навигации Navigation Graph выше слоя BottomSheet
 * ----------------------------------------------
 * @param mainViewModel главная модель приложения
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilGraph(
    mainViewModel: MainViewModel,
    globalNavController: NavHostController
) {
    navigation(startDestination = ProfileRoutes.Notifications.route, route = MainRoutes.UtilRouteID.route) {

        // ------------------------- Профили -------------------------

        composable( // Уведомления
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

        composable( // Настройки
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

        // -----------------------------------------------------------


        // --------------------- Меню объявлений ---------------------

        composable( // Карточка товара
            route = SearchRoutes.AdditionalInfoScreen.route,
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
            AdditionalInfoScreen(globalNavController)
        }

        // -----------------------------------------------------------

    }
}

/**
 * Расширение навигации авторизации Navigation Graph выше слоя BottomSheet
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
