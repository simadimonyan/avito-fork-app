package samaryanin.avitofork.data.database.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAdDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(ad: Favorite)

    @Query("DELETE FROM favorites WHERE favId = :favId")
    suspend fun removeFavorite(favId: Int)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<Favorite>>

}