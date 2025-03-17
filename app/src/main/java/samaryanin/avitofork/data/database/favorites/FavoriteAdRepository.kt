package samaryanin.avitofork.data.database.favorites

import kotlinx.coroutines.flow.Flow

class FavoriteAdRepository(private val favoriteAdDao: FavoriteAdDao) {

    val allFavorites: Flow<List<Favorite>> = favoriteAdDao.getFavorites()

    suspend fun addFavorite(favId: Int) {
        favoriteAdDao.addFavorite(Favorite(favId))
    }

    suspend fun removeFavorite(adId: Int) {
        favoriteAdDao.removeFavorite(adId)
    }

}