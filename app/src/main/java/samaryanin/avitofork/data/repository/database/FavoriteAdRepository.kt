package samaryanin.avitofork.data.repository.database

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import samaryanin.avitofork.data.dao.favorites.FavoriteAdDao
import samaryanin.avitofork.data.models.favorites.AdEntity
import samaryanin.avitofork.data.models.favorites.AdWithFavorite
import samaryanin.avitofork.data.models.favorites.Favorite

@Stable
class FavoriteAdRepository(private val favoriteAdDao: FavoriteAdDao) {

    fun getAllAds(): Flow<List<AdEntity>> = favoriteAdDao.getAllAds() // получить все объявления

    fun getFavoriteAds(): Flow<List<AdEntity>> = favoriteAdDao.getFavoriteAds() // получить избранные

    // все объявления с флагом избранного
    fun getAdsWithFavoriteStatus(): Flow<List<AdWithFavorite>> = favoriteAdDao.getAdsWithFavoriteStatus()

    suspend fun addFavorite(adId: Int) {
        favoriteAdDao.addToFavorites(Favorite(adId))
    }

    suspend fun removeFavorite(adId: Int) {
        favoriteAdDao.removeFromFavorites(adId)
    }

    suspend fun insertAds(ads: List<AdEntity>) {
        favoriteAdDao.insertAds(ads)
    }
}