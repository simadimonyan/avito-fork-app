package samaryanin.avitofork.core.di.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.core.network.KtorClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    @Provides
    @Singleton
    fun provideKtorClient(
        @ApplicationContext context: Context, cacheManager: CacheManager, appStateHolder: AppStateHolder
    ): KtorClient {
        val baseUrl = "https://195.133.48.6/api/v1/"
        return KtorClient(context, baseUrl, cacheManager, appStateHolder)
    }
}
