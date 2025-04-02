package samaryanin.avitofork.data.database.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ads")
data class Ad(
    @PrimaryKey val id: Int,
    val title: String,
    val price: String,
    val address: String,
    val imageUrl: String,
    val isFav: Boolean = false
)