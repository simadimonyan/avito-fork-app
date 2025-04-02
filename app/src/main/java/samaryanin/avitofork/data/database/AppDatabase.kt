package samaryanin.avitofork.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import samaryanin.avitofork.data.database.favorites.Ad
import samaryanin.avitofork.data.database.favorites.Favorite
import samaryanin.avitofork.data.database.favorites.FavoriteAdDao

@Database(entities = [Favorite::class, Ad::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteAdDao(): FavoriteAdDao
}