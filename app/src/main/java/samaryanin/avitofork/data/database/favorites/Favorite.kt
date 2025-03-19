package samaryanin.avitofork.data.database.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey val favId: Int
)