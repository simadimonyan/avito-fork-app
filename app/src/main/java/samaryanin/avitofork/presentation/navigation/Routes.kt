package samaryanin.avitofork.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/*
* Главный Navigation Graph от StartScreen
*/
@Composable
fun GlobalGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = StartScreen
    ) {
        composable<StartScreen> {

        }
    }

}

/*
* Вложенный Navigation Graph от NestedMenuScreen
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