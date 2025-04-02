package samaryanin.avitofork.data.database.favorites

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
class FavoriteAdRepository(private val favoriteAdDao: FavoriteAdDao) {

    fun getAllAds(): Flow<List<Ad>> = favoriteAdDao.getAllAds() // получить все объявления

    fun getFavoriteAds(): Flow<List<Ad>> = favoriteAdDao.getFavoriteAds() // получить избранные

    // все объявления с флагом избранного
    fun getAdsWithFavoriteStatus(): Flow<List<AdWithFavorite>> = favoriteAdDao.getAdsWithFavoriteStatus()

    suspend fun addFavorite(adId: Int) {
        favoriteAdDao.addToFavorites(Favorite(adId))
    }

    suspend fun removeFavorite(adId: Int) {
        favoriteAdDao.removeFromFavorites(adId)
    }

    suspend fun insertAds(ads: List<Ad>) {
        favoriteAdDao.insertAds(ads)
    }
}