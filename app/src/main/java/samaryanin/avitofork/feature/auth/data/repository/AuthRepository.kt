package samaryanin.avitofork.feature.auth.data.repository

import androidx.compose.runtime.Stable
import samaryanin.avitofork.core.network.KtorClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Stable
class AuthRepository @Inject constructor(
    ktorClient: KtorClient
) {

    private val httpClient = ktorClient.httpClient

    suspend fun register(email: String, password: String, name: String) {

    }

    suspend fun verify(email: String, code: String) {

    }

    suspend fun login(email: String, password: String) {

    }

    suspend fun refresh(oldAccessToken: String, oldRefreshToken: String) {

    }

}