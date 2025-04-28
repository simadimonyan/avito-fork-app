package samaryanin.avitofork.core.ui.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.GetFavoriteAdsUseCase
import samaryanin.avitofork.feature.marketplace.domain.usecase.ad.ToggleFavoriteAdUseCase
import javax.inject.Inject


class FavoriteManager @Inject constructor(
    private val getFavoriteAdsUseCase: GetFavoriteAdsUseCase,
    private val toggleFavoriteAdUseCase: ToggleFavoriteAdUseCase,
    private val dataStore: DataStore<Preferences>
) {

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            //loadFromServer()
        }
    }

    fun toggleFavorite(id: String) {
        val isCurrentlyFavorite = _favorites.value.contains(id)
        val updatedFavorites = _favorites.value.toMutableSet().apply {
            if (isCurrentlyFavorite) remove(id) else add(id)
        }
        _favorites.value = updatedFavorites

        scope.launch {
//            toggleFavoriteAdUseCase(id, !isCurrentlyFavorite)
//            loadFromServer()
        }
    }

    fun isFavorite(id: String): Boolean {
        return id in _favorites.value
    }

    fun clearFavorites() {
        _favorites.value = emptySet()
    }

    suspend fun loadFromServer() {
        val remoteAds = getFavoriteAdsUseCase()
        val remoteFavorites = remoteAds.map { it.id }.toSet()
        _favorites.value = remoteFavorites
    }

    suspend fun syncWithServer() {
        val localFavorites = _favorites.value
        val remoteAds = getFavoriteAdsUseCase()
        val remoteFavorites = remoteAds.map { it.id }.toSet()

        val toAddToServer = localFavorites - remoteFavorites
        val toRemoveFromServer = remoteFavorites - localFavorites

        toAddToServer.forEach { id ->
            toggleFavoriteAdUseCase(id, true)
        }

        toRemoveFromServer.forEach { id ->
            toggleFavoriteAdUseCase(id, false)
        }

        loadFromServer()
    }
}