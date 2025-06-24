package samaryanin.avitofork.feature.favorites.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.feature.favorites.ui.components.EmptyFavoritesMessage
import samaryanin.avitofork.feature.favorites.ui.components.FavoriteAdCard
import samaryanin.avitofork.feature.favorites.ui.state.FavoritesScreenViewModel
import samaryanin.avitofork.shared.state.network.NetworkState
import samaryanin.avitofork.shared.ui.components.ShimmerAdCard
import samaryanin.avitofork.shared.ui.components.utils.text.AppTextTitle

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
    val state by viewModel.favoriteAdsState.collectAsState()

    val isRefreshing = state is NetworkState.Loading
    val refreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    val ads = (state as? NetworkState.Success)?.data ?: emptyList()

    val showShimmer = state is NetworkState.Loading

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