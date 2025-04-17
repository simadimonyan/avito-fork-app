package samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.ui.UiState
import samaryanin.avitofork.core.ui.start.data.MainViewModel
import samaryanin.avitofork.core.ui.start.data.state.AppState
import samaryanin.avitofork.core.ui.utils.components.ShimmerAdCard
import samaryanin.avitofork.core.ui.utils.components.utils.text.AppTextTitle
import samaryanin.avitofork.feature.auth.ui.data.AuthState
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad

@Composable
fun FavoritesScreen(
    mainViewModel: MainViewModel,
    globalNavController: NavHostController,
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
        //  mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    FavoritesScreenContent(
        { appState },
        //navigateTo,
        authRequest, { authState },
        globalNavController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    appState: () -> AppState,
    authRequest: () -> Unit,
    authState: () -> AuthState,
    globalNavController: NavHostController
) {
    val viewModel: FavoritesScreenViewModel = hiltViewModel()
    val favoritesState by viewModel.favoriteAdsState.collectAsState()

    val refreshState = rememberPullToRefreshState()
    val isRefreshing = favoritesState is UiState.Loading
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.observeFavorites()
    }

    val ads = when (favoritesState) {
        is UiState.Success -> (favoritesState as UiState.Success<List<Ad>>).data
        is UiState.Loading -> (favoritesState as? UiState.Success<List<Ad>>)?.data ?: emptyList()
        else -> emptyList()
    }
    val showShimmer = favoritesState is UiState.Loading && ads.isEmpty()
    val showError = favoritesState is UiState.Error && ads.isEmpty()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = {
            coroutineScope.launch {
                viewModel.observeFavorites()
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { AppTextTitle("Избранное") },
                )
            }
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                when {
                    showShimmer -> {
                        items(3) {
                            ShimmerAdCard(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }

                    ads.isNotEmpty() -> {
                        items(ads) { ad ->
                            val isFavorite = viewModel.favoriteManager.isFavorite(ad.id)
                            FavoriteAdCard(
                                ad = ad,
                                isFavorite = isFavorite,
                                onLikeClick = { viewModel.toggleFavorite(ad) },
                                globalNavController
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    showError -> {
                        item {
                            Text(
                                text = "Ошибка загрузки: ${(favoritesState as UiState.Error).exception.message}",
                                color = Color.Red,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    else -> {
                        item {
                            EmptyFavoritesMessage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(32.dp)
                            )
                        }
                    }
                }
            }
        }
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