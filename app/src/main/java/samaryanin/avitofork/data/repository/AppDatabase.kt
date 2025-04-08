package samaryanin.avitofork.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import samaryanin.avitofork.data.models.favorites.AdEntity
import samaryanin.avitofork.data.models.favorites.Favorite
import samaryanin.avitofork.data.dao.favorites.FavoriteAdDao

@Database(entities = [Favorite::class, AdEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteAdDao(): FavoriteAdDao
}