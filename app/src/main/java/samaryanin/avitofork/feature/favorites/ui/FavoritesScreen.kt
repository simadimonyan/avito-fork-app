package samaryanin.avitofork.feature.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.favorites.ui.components.EmptyFavoritesMessage
import samaryanin.avitofork.feature.favorites.ui.components.FavoriteAdCard
import samaryanin.avitofork.feature.favorites.ui.state.FavoritesScreenViewModel

@Composable
fun FavoritesScreen(
    globalNavController: NavHostController,
) {
    FavoritesScreenContent(globalNavController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(globalNavController: NavHostController) {
    val viewModel: FavoritesScreenViewModel = hiltViewModel()
    val favorites by viewModel.favorites.collectAsState()
    val isRefreshing by viewModel.isLoading.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = {
            coroutineScope.launch { viewModel.refresh() }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Favorites", style = MaterialTheme.typography.headlineMedium) }
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (favorites.isNotEmpty()) {
                    items(favorites) { ad ->
                        FavoriteAdCard(
                            ad = ad,
                            isFavorite = true,
                            onLikeClick = { viewModel.toggleFavorite(ad) },
                            globalNavController = globalNavController
                        )
                    }
                } else {
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
