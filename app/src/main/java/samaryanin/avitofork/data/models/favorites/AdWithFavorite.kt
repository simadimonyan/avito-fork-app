package samaryanin.avitofork.data.models.favorites

import androidx.room.Embedded

data class AdWithFavorite(
    @Embedded val ad: AdEntity,
    val isFavorite: Boolean
)