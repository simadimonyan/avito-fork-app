package samaryanin.avitofork.data.cache

import kotlinx.coroutines.flow.MutableStateFlow

object FavoriteIds {
    val favIdsFlow = MutableStateFlow<Set<String>>(emptySet())
}