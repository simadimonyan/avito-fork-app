package samaryanin.avitofork.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import samaryanin.avitofork.presentation.screens.auth.LoginScreen
import samaryanin.avitofork.presentation.screens.auth.SignUpScreen
import samaryanin.avitofork.presentation.screens.auth.VerificationScreen
import samaryanin.avitofork.presentation.screens.auth.data.AuthViewModel
import samaryanin.avitofork.presentation.screens.start.StartScreen
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
* Главный Navigation Graph от [StartScreen]
*/
@Composable
fun GlobalGraph(mainViewModel: MainViewModel, navHostController: NavHostController) {

    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = navHostController,
        startDestination = StartScreen
    ) {
        composable<StartScreen> {
            StartScreen(mainViewModel, navHostController)
        }
        composable<Login>(
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
            LoginScreen(authViewModel, navHostController)
        }
        composable<SignUp>(
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
            SignUpScreen(authViewModel, navHostController)
        }
        composable<VerificationScreen>(
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
            VerificationScreen(authViewModel, mainViewModel, navHostController)
        }
    }

}

/**
* Вложенный Navigation Graph от [NestedMenuScreen]
*/
@Composable
fun NestedGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = NestedMenuScreen
    ) {
        composable<NestedMenuScreen> {

        }
    }

}
