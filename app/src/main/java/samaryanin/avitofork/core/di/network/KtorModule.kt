package samaryanin.avitofork.core.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import samaryanin.avitofork.core.network.KtorClient

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    @Provides
    fun createHttpClient(): KtorClient {
        return KtorClient("https://194.54.159.160/api/v1/")
    }

}