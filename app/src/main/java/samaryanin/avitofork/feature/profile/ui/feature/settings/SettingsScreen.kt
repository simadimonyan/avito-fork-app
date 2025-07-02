package samaryanin.avitofork.feature.profile.ui.feature.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.feature.profile.ui.components.settings.SettingsList
import samaryanin.avitofork.feature.profile.ui.state.settings.SettingsViewModel
import samaryanin.avitofork.shared.ui.theme.navigationSelected

@Preview
@Composable
fun SettingsPreview() {
    SettingsAuthorizedContent(onExit = { true })
}

@Composable
fun SettingsScreen(
    navController: NavHostController,
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val appState by viewModel.appStateHolder.appState.collectAsState()

    if (appState.isLoggedIn) {
        SettingsAuthorizedContent(
            onExit = {
                navController.navigateUp()
                true
            },
            onLogoutConfirmed = {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo("settings") { inclusive = true }
                }
            }
        )
    }
    else {
        SettingsUnauthorizedContent(
            onExit = {
                navController.navigateUp()
                true
            }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAuthorizedContent(
    onExit: () -> Boolean,
    onLogoutConfirmed: () -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        "Настройки",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = { onExit() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = navigationSelected
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingsList(
                onDismiss = { onExit() },
                onClearHistory = { /* TODO: очистить историю */ },
                onRateApp = { /* TODO: открыть Play Store */ },
                onNavigate = { screen -> /* TODO: навигация */ }
            )

            Column {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { showLogoutDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, color = Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp)
                ) {
                    Text(
                        "Выйти",
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Выход") },
                    text = { Text("Вы уверены, что хотите выйти из аккаунта?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            onLogoutConfirmed()
                        }) {
                            Text("Выйти")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text("Отмена")
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUnauthorizedContent(
    onExit: () -> Boolean
) {
    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        "Настройки",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = { onExit() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = navigationSelected
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingsList(
                onDismiss = { onExit() },
                onClearHistory = { /* TODO: очистить историю */ },
                onRateApp = { /* TODO: открыть Play Store */ },
                onNavigate = { screen -> /* TODO: навигация */ }
            )
        }
    }
}