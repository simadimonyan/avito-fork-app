package samaryanin.avitofork.app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import samaryanin.avitofork.app.activity.data.AppEvent
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.app.navigation.GlobalGraph
import samaryanin.avitofork.shared.ui.theme.AvitoForkTheme
import samaryanin.avitofork.shared.ui.theme.adaptive.LocaleDimensions
import samaryanin.avitofork.shared.ui.theme.adaptive.layout

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey("a84df13b-02f9-4b1b-8cc6-84744f8f51f5")
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AvitoForkTheme {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    CompositionLocalProvider(LocaleDimensions provides layout.getThemeSize(maxWidth)) {

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
    }
}

