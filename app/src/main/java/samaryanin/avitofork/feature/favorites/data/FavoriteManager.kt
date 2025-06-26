package samaryanin.avitofork.feature.favorites.data

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.favorites.domain.usecases.GetFavoriteAdsUseCase
import samaryanin.avitofork.feature.favorites.domain.usecases.ToggleFavoriteAdUseCase
import javax.inject.Inject

@Stable
class FavoriteManager @Inject constructor(
    private val getFavoriteAdsUseCase: GetFavoriteAdsUseCase,
    private val toggleFavoriteAdUseCase: ToggleFavoriteAdUseCase,
    private val cacheManager: CacheManager
) {
    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    suspend fun initialize() {
        syncWithServer()
    }

    suspend fun toggleFavorite(id: String) {
        val wasFavorite = _favorites.value.contains(id)
        val isNowFavorite = !wasFavorite
        val updated = _favorites.value.toMutableSet().apply {
            if (isNowFavorite) add(id) else remove(id)
        }
        _favorites.value = updated
        toggleFavoriteAdUseCase(id, isNowFavorite)
        syncWithServer()
    }

    suspend fun syncWithServer() {
        _favorites.value = if (isAuthorized()) {
            getFavoriteAdsUseCase().map { it.id }.toSet()
        } else {
            emptySet()
        }
    }

    fun isFavorite(id: String): Boolean = id in _favorites.value

    private fun isAuthorized(): Boolean =
        cacheManager.preferences.getString("authToken", null) != null
}