package samaryanin.avitofork.core.ui.start

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import samaryanin.avitofork.core.ui.navigation.GlobalGraph
import samaryanin.avitofork.core.ui.start.data.state.AppEvent
import samaryanin.avitofork.core.ui.start.data.MainViewModel
import samaryanin.avitofork.core.ui.utils.theme.AvitoForkTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            AvitoForkTheme {

                val viewModel: MainViewModel = hiltViewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White)
                        .windowInsetsPadding(WindowInsets.systemBars)
                ) { innerPadding ->

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.White)
                    ) {

                        // Рекомпозиция только при запуске
                        LaunchedEffect(true) {
                            viewModel.handleEvent(AppEvent.RestoreCache)
                            viewModel.handleEvent(AppEvent.FirstStartUp(false))
                        }

                        GlobalGraph(viewModel)

                    }
                }
            }
        }
    }
}

