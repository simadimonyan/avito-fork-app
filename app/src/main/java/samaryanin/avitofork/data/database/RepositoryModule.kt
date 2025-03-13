package samaryanin.avitofork.data.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import samaryanin.avitofork.data.database.favorites.FavoriteAdDao
import samaryanin.avitofork.data.database.favorites.FavoriteAdRepository

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun provideFavoriteAdRepository(dao: FavoriteAdDao): FavoriteAdRepository {
        return FavoriteAdRepository(dao)
    }
}