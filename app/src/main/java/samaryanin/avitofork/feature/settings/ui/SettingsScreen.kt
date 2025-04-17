package samaryanin.avitofork.feature.settings.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.core.ui.utils.theme.navigationSelected

@Preview
@Composable
fun SettingsPreview() {
    SettingsContent(onExit = {true})
}

@Composable
fun SettingsScreen(
    navController: NavHostController,
) {
    val onExit = {
        navController.navigateUp()
    }

    SettingsContent(
        onExit = onExit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(onExit: () -> Boolean) {
    Scaffold(
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { onExit() }
                    ) {
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
                onLogout = { /* TODO: показать диалог */ },
                onDismiss = { onExit() },
                onClearHistory = { /* TODO: очистить историю */ },
                onRateApp = { /* TODO: открыть Play Store */ },
                onNavigate = { screen ->
                    // TODO: навигация по названию экрана
                }
            )
        }
    }
}

private enum class SettingsSection(val title: String?) {
    Search("Регион для поиска"),
    General(null),
    Info(null),
    Legal(null)
}

private enum class SettingsItem(
    val section: SettingsSection,
    val title: String,
    val showDisclosure: Boolean = true,
    val isBlue: Boolean = false
) {
    Region(SettingsSection.Search, "Краснодар"),
    Notifications(SettingsSection.General, "Уведомления"),
    ClearHistory(SettingsSection.General, "Очистить историю поисков", showDisclosure = false),
    Appearance(SettingsSection.General, "Тема, как на телефоне"),
    Business(SettingsSection.Info, "Решения для бизнеса"),
    Help(SettingsSection.Info, "Помощь"),
    Rating(SettingsSection.Legal, "Оценить приложение", showDisclosure = false, isBlue = true),
    Rules(SettingsSection.Legal, "Правила Авито"),
    Terms(SettingsSection.Legal, "Оферта на оказание услуг"),
    Recommendations(SettingsSection.Legal, "Рекомендательные технологии")
}

@Composable
fun SettingsList(
    onLogout: () -> Unit,
    onDismiss: () -> Unit,
    onClearHistory: () -> Unit,
    onRateApp: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val sections = SettingsSection.values()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (section in sections) {
                val items = SettingsItem.values().filter { it.section == section }

                if (items.isNotEmpty()) {
                    if (section.title != null) {
                        item {
                            Text(
                                text = section.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                            )
                        }
                    }

                    items(items) { item ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = item.title,
                                    color = if (item.isBlue) Color(0xFF007AFF) else Color.Black
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    when (item) {
                                        SettingsItem.ClearHistory -> onClearHistory()
                                        SettingsItem.Rating -> onRateApp()
                                        else -> onNavigate(item.name)
                                    }
                                },
                            trailingContent = {
                                if (item.showDisclosure) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = Color.Gray,
                                        modifier = Modifier.rotate(180f)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = onLogout,
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
}