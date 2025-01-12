package samaryanin.avitofork.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import samaryanin.avitofork.presentation.screens.start.components.StartScreen

/**
* Главный Navigation Graph от [StartScreen]
*/
@Composable
fun GlobalGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = StartScreen
    ) {
        composable<StartScreen> {
            StartScreen(hiltViewModel())
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