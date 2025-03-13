package samaryanin.avitofork.presentation.screens.menu.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.presentation.screens.auth.data.AuthState
import samaryanin.avitofork.presentation.screens.start.data.AppEvent
import samaryanin.avitofork.presentation.screens.start.data.AppState
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle

data class FavoriteAd(
    val id: Int,
    val title: String,
    val price: String,
    val address: String,
    val imageUrl: String
)

@Composable
fun FavoritesScreen(
    mainViewModel: MainViewModel,
    globalNavController: NavHostController
) {

    val appState by mainViewModel.appStateStore.appStateHolder.appState.collectAsState()
    val authState by mainViewModel.appStateStore.authStateHolder.authState.collectAsState()

    val navigateTo = 0
//        { index: Int ->
//        when (index) {
//            0 -> { // 0 - индекс навигации на экран уведомлений
//                globalNavController.navigate(ProfileRoutes.Notifications.route) {
//                    popUpTo(globalNavController.graph.findStartDestination().id) {
//                        saveState = true
//                    }
//                    restoreState = true
//                    launchSingleTop = true
//                }
//            }
//            1 -> { // 1 - индекс навигации на экран настроек
//                globalNavController.navigate(SettingsRoutes.Settings.route) {
//                    popUpTo(globalNavController.graph.findStartDestination().id) {
//                        saveState = true
//                    }
//                    restoreState = true
//                    launchSingleTop = true
//                }
//            }
//        }
//    }

    // обработчик событий для AuthBottomSheet
    val authRequest = {
        mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    FavoritesScreenContent({ appState },
        //navigateTo,
        authRequest, { authState })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    appState: () -> AppState,
   // navigateTo: (Int) -> Unit,
    authRequest: () -> Unit,
    authState: () -> AuthState
){
// Пример списка избранных объявлений
    val favoriteAds = listOf(
        FavoriteAd(
            1,
            "Квартира в центре",
            "50 000 ₽",
            "Москва, ул. Ленина",
            "https://example.com/image1.jpg"
        ),
        FavoriteAd(2, "Дом у озера", "80 000 ₽", "Подмосковье", ""),
        FavoriteAd(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        FavoriteAd(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        FavoriteAd(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        FavoriteAd(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        FavoriteAd(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
        FavoriteAd(3, "Студия", "35 000 ₽", "Санкт-Петербург", ""),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppTextTitle("Избранное") },
            )
        }
    ) { padding ->
        if (favoriteAds.isEmpty()) {
            EmptyFavoritesMessage(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(favoriteAds) { ad ->
                    FavoriteAdCard(ad = ad)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


// Компонент карточки объявления
@Composable
fun FavoriteAdCard(ad: FavoriteAd) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardColors(
            Color.Transparent,
            Color.Black,
            Color.Transparent,
            Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Фото объявления
            Image(
                painter = painterResource(R.drawable.testimg),
                //  painter = rememberAsyncImageLoader(ad.imageUrl),
                contentDescription = "Фото объявления",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Текстовая информация
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = ad.price,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.title,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.address,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

// Сообщение, если избранное пустое
@Composable
fun EmptyFavoritesMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "У вас пока нет избранных объявлений",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// Предпросмотр для дизайна
@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MaterialTheme {
       // FavoritesScreen()
    }
}