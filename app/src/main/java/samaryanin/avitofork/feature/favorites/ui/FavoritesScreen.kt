package samaryanin.avitofork.feature.favorites.ui

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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import samaryanin.avitofork.shared.state.network.NetworkState
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.feature.favorites.ui.state.FavoritesScreenViewModel
import samaryanin.avitofork.shared.ui.components.ShimmerAdCard
import samaryanin.avitofork.shared.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.favorites.ui.components.EmptyFavoritesMessage
import samaryanin.avitofork.feature.favorites.ui.components.FavoriteAdCard

@Composable
fun FavoritesScreen(
    mainViewModel: MainViewModel,
    globalNavController: NavHostController,
) {
    FavoritesScreenContent(globalNavController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    globalNavController: NavHostController
) {
    val viewModel: FavoritesScreenViewModel = hiltViewModel()
    val favoritesState by viewModel.favoriteAdsState.collectAsState()

    val refreshState = rememberPullToRefreshState()
    val isRefreshing = favoritesState is NetworkState.Loading
    val coroutineScope = rememberCoroutineScope()

    val ads = when (favoritesState) {
        is NetworkState.Success -> (favoritesState as NetworkState.Success<List<Ad>>).data
        is NetworkState.Loading -> (favoritesState as? NetworkState.Success<List<Ad>>)?.data ?: emptyList()
        else -> emptyList()
    }

    val showShimmer = favoritesState is NetworkState.Loading
    val showError = favoritesState is NetworkState.Error

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = {
            coroutineScope.launch {
                viewModel.loadFavorites()
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

//                    showError -> {
//                        item {
//                            Text(
//                                text = "Ошибка загрузки: ${(favoritesState as UiState.Error).exception.message}",
//                                color = Color.Red,
//                                modifier = Modifier.padding(8.dp)
//                            )
//                        }
//                    }

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