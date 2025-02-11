package samaryanin.avitofork.presentation.screens.start

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import samaryanin.avitofork.presentation.navigation.GlobalGraph
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel
import samaryanin.avitofork.presentation.ui.theme.AvitoForkTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AvitoForkTheme {

                val viewModel: MainViewModel = hiltViewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {

                        // Рекомпозиция только при запуске
                        LaunchedEffect(true) {
                            viewModel.handleEvent(AppEvent.FirstStartUp(false))

                        }

                        // Сокрытие системной панели навигации
                        WindowCompat.setDecorFitsSystemWindows(window, false)

                        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
                        insetsController.hide(WindowInsetsCompat.Type.navigationBars())
                        insetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

                        GlobalGraph(viewModel)

                    }
                }
            }
        }
    }
}

