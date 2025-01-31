package samaryanin.avitofork.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import samaryanin.avitofork.presentation.screens.auth.login.LoginScreen
import samaryanin.avitofork.presentation.screens.auth.signup.SignUpScreen
import samaryanin.avitofork.presentation.screens.auth.signup.VerificationCodeScreen
import samaryanin.avitofork.presentation.screens.auth.signup.data.SignUpEvent
import samaryanin.avitofork.presentation.screens.auth.signup.data.SignUpState
import samaryanin.avitofork.presentation.screens.auth.signup.data.SignUpViewModel
import samaryanin.avitofork.presentation.screens.start.StartScreen
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

/**
* Главный Navigation Graph от [StartScreen]
*/
@Composable
fun GlobalGraph(mainViewModel: MainViewModel, navHostController: NavHostController) {

    val signUpViewModel: SignUpViewModel = hiltViewModel()

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

            // обработчик выхода
            val onExit = {
                navHostController.popBackStack()
            }

            LoginScreen(onExit)
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

            // обработчик выхода
            val onExit = {
                navHostController.popBackStack()
            }

            // обработчик глобальной навигации для AuthBottomSheet
            val navigateTo = { index: Int ->
                when (index) {
                    0 -> { // 0 - индекс экрана верификации номера телефона
                        navHostController.navigate(VerificationNumScreen) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }

            @Composable
            fun getState(): State<SignUpState> {
                return signUpViewModel.state.collectAsState()
            }

            fun setState(state: SignUpState) {
                signUpViewModel.handleEvent(SignUpEvent.UpdateState(state))
            }

            SignUpScreen(onExit, navigateTo, signUpViewModel::handleEvent, getState(), ::setState)

        }
        composable<VerificationNumScreen>(
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

            // обработчик выхода
            val onExit = {
                navHostController.navigate(SignUp) {
                    popUpTo(navHostController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            VerificationCodeScreen(onExit)
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
