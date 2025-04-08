package samaryanin.avitofork.di.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import samaryanin.avitofork.data.dao.favorites.FavoriteAdDao
import samaryanin.avitofork.data.repository.database.FavoriteAdRepository

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideFavoriteAdRepository(dao: FavoriteAdDao): FavoriteAdRepository {
        return FavoriteAdRepository(dao)
    }

}