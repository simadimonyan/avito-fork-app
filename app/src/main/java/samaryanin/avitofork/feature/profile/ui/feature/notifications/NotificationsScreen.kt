package samaryanin.avitofork.feature.profile.ui.feature.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.shared.ui.theme.navigationSelected

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun NotificationsPreview() {
    NotificationsContent {true}
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param navController контроллер навигации
 */
@Composable
fun NotificationsScreen(
    navController: NavHostController,
) {

    val onExit = {
        navController.navigateUp()
    }

    NotificationsContent(onExit)
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param onExit обработчик навигации выхода
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsContent(onExit: () -> Boolean) {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                title = { Text("Уведомления", color = Color.Black, fontWeight = FontWeight.Normal, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        onExit()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = navigationSelected)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(bottom = 70.dp),
                text = "У вас нет новых уведомлений",
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}