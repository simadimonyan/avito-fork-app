package samaryanin.avitofork.presentation.screens.start.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import samaryanin.avitofork.presentation.navigation.NestedGraph
import samaryanin.avitofork.presentation.screens.start.state.StartViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel,
) {
    val navHostController = rememberNavController()

    NestedGraph(navHostController = navHostController)


}