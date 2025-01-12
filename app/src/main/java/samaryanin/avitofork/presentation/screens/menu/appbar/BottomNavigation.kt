package samaryanin.avitofork.presentation.screens.menu.appbar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.presentation.ui.theme.navigationSelected


@Composable
fun BottomAppNavigation() {

    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {

            // Навигация в меню поиска
            BottomNavigationItem(
                modifier = Modifier.wrapContentSize(),
                label = { Text("Поиск", fontSize = 9.sp,
                    maxLines = 1,
                    color = if (selectedIndex == 0) navigationSelected else Color.Gray) },
                onClick = {
                    selectedIndex = 0
                    //TODO
                },
                icon = { Icon(Icons.Filled.Search, contentDescription = "Search",
                    tint = if (selectedIndex == 0) navigationSelected else Color.Gray) },
                selected = true,
                selectedContentColor = Color.White
            )

            // Навигация в меню избранного
            BottomNavigationItem(
                modifier = Modifier.wrapContentSize(),
                label = { Text("Избранное", fontSize = 9.sp,
                    maxLines = 1,
                    color = if (selectedIndex == 1) navigationSelected else Color.Gray) },
                onClick = {
                    selectedIndex = 1
                    //TODO
                },
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Like",
                    tint = if (selectedIndex == 1) navigationSelected else Color.Gray) },
                selected = false,
                selectedContentColor = Color.White
            )

            // Навигация в меню объявлений
            BottomNavigationItem(
                modifier = Modifier.wrapContentSize(),
                label = { Text("Объявления", fontSize = 9.sp,
                    maxLines = 1,
                    color = if (selectedIndex == 2) navigationSelected else Color.Gray) },
                onClick = {
                    selectedIndex = 2
                    //TODO
                },
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Billboard",
                    tint = if (selectedIndex == 2) navigationSelected else Color.Gray) },
                selected = false,
                selectedContentColor = Color.White
            )

            // Навигация в меню сообщений
            BottomNavigationItem(
                modifier = Modifier.wrapContentSize(),
                label = { Text("Сообщения", fontSize = 9.sp,
                    maxLines = 1,
                    color = if (selectedIndex == 3) navigationSelected else Color.Gray) },
                onClick = {
                    selectedIndex = 3
                    //TODO
                },
                icon = { Icon(Icons.Filled.Email, contentDescription = "Messages",
                    tint = if (selectedIndex == 3) navigationSelected else Color.Gray) },
                selected = false,
                selectedContentColor = Color.White
            )

            // Навигация в меню профиля
            BottomNavigationItem(
                modifier = Modifier.wrapContentSize(),
                label = { Text("Профиль", fontSize = 9.sp,
                    maxLines = 1,
                    color = if (selectedIndex == 4) navigationSelected else Color.Gray) },
                onClick = {
                    selectedIndex = 4
                    //TODO
                },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile",
                    tint = if (selectedIndex == 4) navigationSelected else Color.Gray) },
                selected = false,
                selectedContentColor = Color.White
            )

        }

    }

}