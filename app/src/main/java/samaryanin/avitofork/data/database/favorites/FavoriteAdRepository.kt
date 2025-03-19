package samaryanin.avitofork.data.database.favorites

import kotlinx.coroutines.flow.Flow

class FavoriteAdRepository(private val favoriteAdDao: FavoriteAdDao) {

    val allFavorites: Flow<List<Ad>> = favoriteAdDao.getFavoriteAds()
    val adsWithFavoriteStatus: Flow<List<AdWithFavorite>> = favoriteAdDao.getAdsWithFavoriteStatus()

    suspend fun addFavorite(favId: Int) {
        favoriteAdDao.addToFavorites(Favorite(favId))
    }

    suspend fun removeFavorite(adId: Int) {
        favoriteAdDao.removeFromFavorites(adId)
    }

    suspend fun insertAds(ads: List<Ad>) {
        favoriteAdDao.insertAds(ads)
    }
}