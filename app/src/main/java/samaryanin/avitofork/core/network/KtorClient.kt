package samaryanin.avitofork.core.network

import android.content.Context
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.dimagor555.avito.auth.request.RefreshRequestDto
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.core.cache.CacheManager
import samaryanin.avitofork.feature.auth.data.dto.AuthToken
import javax.inject.Inject

class KtorClient @Inject constructor(
    private val context: Context,
    private val baseUrl: String,
    private val cacheManager: CacheManager,
    private val appStateHolder: AppStateHolder
) {

    val httpClient = HttpClient(CIO) {

        defaultRequest {
            url {
                takeFrom(baseUrl)
            }
        }

        install(DefaultRequest) {

            val deviceId = DeviceIdProvider.getDeviceId(context)
            header("device-id", deviceId)
            header(HttpHeaders.ContentType, ContentType.Application.Json)

            val token = cacheManager.getAuthToken()
            if (token.accessToken.isNotBlank()) {
                header(HttpHeaders.Authorization, "Bearer ${token.accessToken}")
            }

        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }

        install(Auth) {

            bearer {

                loadTokens {
                    val token = cacheManager.getAuthToken()
                    if (token.accessToken.isBlank() || token.refreshToken.isBlank()) {
                        logout()
                        null
                    } else
                        BearerTokens(token.accessToken, token.refreshToken)
                }

                refreshTokens {
                    val old = cacheManager.getAuthToken()
                    try {
                        val new = client.post("auth/refresh") {
                            markAsRefreshTokenRequest()
                            contentType(ContentType.Application.Json)
                            setBody(RefreshRequestDto(old.accessToken, old.refreshToken))
                        }.body<AuthToken>()
                        cacheManager.saveAuthToken(new)
                        BearerTokens(new.accessToken, new.refreshToken)
                    } catch (e: Exception) {
                        Log.d("RefreshTokens", e.toString())
                        logout()
                        null
                    }
                }

            }

        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
                encodeDefaults = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor", message)
                }
            }
            level = LogLevel.INFO
            sanitizeHeader { header -> header == HttpHeaders.Authorization }

        }
    }

    private fun logout() {
        appStateHolder.logout()
        cacheManager.saveAppState(appStateHolder.appState.value)
        cacheManager.clearAuthToken()
    }

}