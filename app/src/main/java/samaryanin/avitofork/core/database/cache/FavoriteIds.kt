package samaryanin.avitofork.core.database.cache

import kotlinx.coroutines.flow.MutableStateFlow

object FavoriteIds {
    val favIdsFlow = MutableStateFlow<Set<String>>(emptySet())
}