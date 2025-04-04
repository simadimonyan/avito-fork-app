package samaryanin.avitofork.presentation.screens.menu.appbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.presentation.screens.start.data.AppState
import samaryanin.avitofork.presentation.ui.theme.navigationSelected

@Composable
fun BottomAppNavigation(
    appState: AppState,
    mainScreenNavigateTo: (Int) -> Unit,
    onAuthRequest: () -> Unit,
    currentScreen: Int
) {

    var selectedIndex = currentScreen

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        Card(
            modifier = Modifier
                .wrapContentSize()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .padding(top = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {

            BottomNavigation(
                windowInsets = WindowInsets(0),
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {

                // Навигация в меню поиска
                BottomNavigationItem(
                    modifier = Modifier.wrapContentSize(),
//                label = { Text("Поиск", fontSize = 9.sp,
//                    maxLines = 1,
//                    color = if (selectedIndex == 0) navigationSelected else Color.Gray) },
                    onClick = {
                        if (selectedIndex != 0) {
                            selectedIndex = 0
                            mainScreenNavigateTo(0)
                        }
                    },
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search",
                        tint = if (selectedIndex == 0) navigationSelected else Color.Gray) },
                    selected = currentScreen == 0,
                    selectedContentColor = Color.White
                )

                // Навигация в меню избранного
                BottomNavigationItem(
                    modifier = Modifier.wrapContentSize(),
//                label = { Text("Избранное", fontSize = 9.sp,
//                    maxLines = 1,
//                    color = if (selectedIndex == 1) navigationSelected else Color.Gray) },
                    onClick = {
                        if (selectedIndex != 1) {
                            selectedIndex = 1
                            mainScreenNavigateTo(1)
                        }
                    },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Like",
                        tint = if (selectedIndex == 1) navigationSelected else Color.Gray) },
                    selected = currentScreen == 1,
                    selectedContentColor = Color.White
                )

//            // Навигация в меню объявлений
//            BottomNavigationItem(
//                modifier = Modifier.wrapContentSize(),
//                label = { Text("Объявления", fontSize = 9.sp,
//                    maxLines = 1,
//                    color = if (selectedIndex == 2) navigationSelected else Color.Gray) },
//                onClick = {
//                    selectedIndex = 2
//                    //TODO
//                },
//                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Billboard",
//                    tint = if (selectedIndex == 2) navigationSelected else Color.Gray) },
//                selected = false,
//                selectedContentColor = Color.White
//            )

                // Навигация в меню сообщений
                BottomNavigationItem(
                    modifier = Modifier.wrapContentSize(),
//                label = { Text("Сообщения", fontSize = 9.sp,
//                    maxLines = 1,
//                    color = if (selectedIndex == 2) navigationSelected else Color.Gray) },
                    onClick = {

                        // если пользователь неавторизован
                        if (!appState.isLoggedIn) {
                            onAuthRequest()
                        }
                        else {
                            if (selectedIndex != 2) {
                                selectedIndex = 2
                                mainScreenNavigateTo(2)
                            }
                        }

                    },
                    icon = { Icon(Icons.Filled.Email, contentDescription = "Messages",
                        tint = if (selectedIndex == 2) navigationSelected else Color.Gray) },
                    selected = currentScreen == 2,
                    selectedContentColor = Color.White
                )

                // Навигация в меню профиля
                BottomNavigationItem(
                    modifier = Modifier.wrapContentSize(),
//                label = { Text("Профиль", fontSize = 9.sp,
//                    maxLines = 1,
//                    color = if (selectedIndex == 3) navigationSelected else Color.Gray) },
                    onClick = {
                        if (selectedIndex != 3) {
                            selectedIndex = 3
                            mainScreenNavigateTo(3)
                        } // 3 - индекс профиля и управления объявлениями
                    },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile",
                        tint = if (selectedIndex == 3) navigationSelected else Color.Gray) },
                    selected = currentScreen == 3,
                    selectedContentColor = Color.White
                )

            }

        }

    }

}