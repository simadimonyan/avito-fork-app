package samaryanin.avitofork.feature.profile.ui.components.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsList(
    onDismiss: () -> Unit,
    onClearHistory: () -> Unit,
    onRateApp: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val sections = SettingsSection.entries.toTypedArray()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp).background(Color.White),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (section in sections) {
                val items = SettingsItem.entries.filter { it.section == section }

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
                                .background(Color.Transparent)
                                .fillMaxWidth()
                                .clickable {
                                    when (item) {
                                        SettingsItem.ClearHistory -> onClearHistory()
                                        //SettingsItem.Rating -> onRateApp()
                                        else -> onNavigate(item.name)
                                    }
                                },
                            colors = ListItemDefaults.colors(containerColor = Color.White),
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
    }
}