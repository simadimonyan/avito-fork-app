package samaryanin.avitofork.feature.auth.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.feature.auth.ui.feature.verification.VerificationScreen
import samaryanin.avitofork.feature.auth.ui.state.AuthViewModel
import samaryanin.avitofork.feature.auth.ui.feature.login.LoginScreen
import samaryanin.avitofork.feature.auth.ui.feature.signup.CreateProfileScreen
import samaryanin.avitofork.feature.auth.ui.feature.signup.SignUpScreen

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
            LoginScreen(authViewModel, mainViewModel, globalNavController)
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
//            arguments = listOf(
//                navArgument("createProfile") {
//                    type = NavType.BoolType
//                }
//            ),
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
        ) { //backStackEntry ->
            //val createProfile = backStackEntry.arguments?.getBoolean("createProfile") ?: false
            VerificationScreen(authViewModel, mainViewModel, globalNavController) //, createProfile
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
