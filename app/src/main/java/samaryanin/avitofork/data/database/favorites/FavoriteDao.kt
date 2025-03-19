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
    fun getAllAds(): Flow<List<Ad>> // Получить все объявления

    @Query(
        """
        SELECT ads.*, 
       EXISTS (SELECT 1 FROM favorites WHERE favorites.favId = ads.id) AS isFavorite
FROM ads
    """
    )
    fun getAdsWithFavoriteStatus(): Flow<List<AdWithFavorite>> // Все объявления с флагом избранного

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favorite: Favorite) // Добавить в избранное

    @Query("DELETE FROM favorites WHERE favId = :adId")
    suspend fun removeFromFavorites(adId: Int) // Удалить из избранного

    @Query(
        """
        SELECT ads.* 
        FROM ads 
        INNER JOIN favorites ON ads.id = favorites.favId
    """
    )
    fun getFavoriteAds(): Flow<List<Ad>> // Получить только избранные объявления
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

