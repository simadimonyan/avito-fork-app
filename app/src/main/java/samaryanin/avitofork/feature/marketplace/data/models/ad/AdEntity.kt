package samaryanin.avitofork.feature.marketplace.data.models.ad

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ads")
data class AdEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: String,
    val address: String,
    val imageUrl: String,
)