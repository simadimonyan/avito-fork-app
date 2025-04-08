package samaryanin.avitofork.data.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey val favId: Int
)