package samaryanin.avitofork.data.database.favorites

import androidx.room.Embedded

data class AdWithFavorite(
    @Embedded val ad: Ad,
    val isFavorite: Boolean
)