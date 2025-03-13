package samaryanin.avitofork.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import samaryanin.avitofork.data.database.favorites.FavoriteAdDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
  //  @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavoriteAdDao(database: AppDatabase): FavoriteAdDao {
        return database.favoriteAdDao()
    }
}