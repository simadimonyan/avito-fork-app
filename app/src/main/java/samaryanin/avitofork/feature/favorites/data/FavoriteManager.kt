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
        if (isAuthorized()) {
            val remoteAds = getFavoriteAdsUseCase()
            _favorites.value = remoteAds.mapTo(mutableSetOf()) { it.id }
        } else {
            _favorites.value = emptySet()
        }
    }

    fun toggleFavorite(id: String) {
        val updated = _favorites.value.toMutableSet().apply {
            if (!add(id)) remove(id)
        }
        _favorites.value = updated
    }

    suspend fun syncWithServer() {
        if (!isAuthorized()) return
        val remote = getFavoriteAdsUseCase().map { it.id }.toSet()
        val local = _favorites.value

        val toAdd = local - remote
        val toRemove = remote - local

        toAdd.forEach { toggleFavoriteAdUseCase(it, true) }
        toRemove.forEach { toggleFavoriteAdUseCase(it, false) }

        _favorites.value = getFavoriteAdsUseCase().map { it.id }.toSet()
    }

    fun isFavorite(id: String): Boolean = id in _favorites.value

    fun clear() {
        _favorites.value = emptySet()
    }

    private fun isAuthorized(): Boolean =
        cacheManager.preferences.getString("authToken", null) != null
}