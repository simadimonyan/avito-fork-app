package samaryanin.avitofork.data.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ads")
data class Ad(
    @PrimaryKey val id: Int,
    val title: String,
    val price: String,
    val address: String,
    val imageUrl: String,
)