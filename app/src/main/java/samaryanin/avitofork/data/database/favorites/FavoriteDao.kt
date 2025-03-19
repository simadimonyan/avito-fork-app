package samaryanin.avitofork.data.database.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAds(ads: List<Ad>) // Добавить несколько объявлений

    @Query("SELECT * FROM ads")
    fun getAllAds(): Flow<List<Ad>>

    @Query("""
        SELECT ads.*, 
               CASE WHEN favorites.favId IS NOT NULL THEN 1 ELSE 0 END AS isFavorite
        FROM ads
        LEFT JOIN favorites ON ads.id = favorites.favId
    """)
     fun getAdsWithFavoriteStatus(): Flow<List<AdWithFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE favId = :adId")
    suspend fun removeFromFavorites(adId: Int)

    @Query("SELECT * FROM ads INNER JOIN favorites ON ads.id = favorites.favId")
    fun getFavoriteAds(): Flow<List<Ad>>
}


//@Dao
//interface FavoriteAdDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addFavorite(ad: Favorite)
//
//    @Query("DELETE FROM favorites WHERE favId = :favId")
//    suspend fun removeFavorite(favId: Int)
//
//    @Query("SELECT * FROM favorites")
//    fun getFavorites(): Flow<List<Favorite>>
//}

