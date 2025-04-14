package samaryanin.avitofork.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteManager @Inject constructor(  // Добавить @Inject
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_ad_ids")
    }

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        scope.launch {
            dataStore.data
                .collect { preferences ->
                    _favorites.value = preferences[FAVORITES_KEY] ?: emptySet()
                }
        }
    }

    fun toggleFavorite(id: String) {
        val currentFavorites = _favorites.value.toMutableSet()
        if (currentFavorites.contains(id)) {
            currentFavorites.remove(id)
        } else {
            currentFavorites.add(id)
        }
        _favorites.value = currentFavorites
        saveFavorites(currentFavorites)
    }

    private fun saveFavorites(favoritesSet: Set<String>) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[FAVORITES_KEY] = favoritesSet
            }
        }
    }

    fun isFavorite(id: String): Boolean {
        return id in _favorites.value
    }

    fun clearFavorites() {
        _favorites.value = emptySet()
        saveFavorites(emptySet())
    }

    fun syncWithServer() {
        // Логика синхронизации с сервером
    }
}
