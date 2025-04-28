package samaryanin.avitofork.core.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.dimagor555.avito.auth.request.RefreshRequestDto
import samaryanin.avitofork.core.database.cache.CacheManager
import samaryanin.avitofork.core.utils.DeviceIdProvider
import samaryanin.avitofork.feature.auth.data.dto.AuthToken
import javax.inject.Inject

class KtorClient @Inject constructor(
    private val context: Context,
    private val baseUrl: String,
    private var cacheManager: CacheManager
) {

    private val gson = Gson()

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

            //val prefs = context.getSharedPreferences("encrypted_prefs", Context.MODE_PRIVATE)
            val json = cacheManager.preferences.getString("authToken", null)

            val type = object : TypeToken<AuthToken>() {}.type
            var token = AuthToken()

            if (json != null) {
                val state: AuthToken = try {
                    gson.fromJson(json, type)
                } catch (_: Exception) { AuthToken() }
                token = state
            }

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

                    //val prefs = context.getSharedPreferences("encrypted_prefs", Context.MODE_PRIVATE)
                    val json = cacheManager.preferences.getString("authToken", null)

                    val type = object : TypeToken<AuthToken>() {}.type
                    var token = AuthToken()

                    if (json != null) {
                        val state: AuthToken = try {
                            gson.fromJson(json, type)
                        } catch (_: Exception) { AuthToken() }
                        token = state
                    }

                    BearerTokens(token.accessToken, token.refreshToken)

                }

                refreshTokens {

                    var token = this.client.post("auth/refresh") {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                        setBody(
                            RefreshRequestDto(
                                oldTokens!!.accessToken,
                                oldTokens!!.refreshToken!!
                            )
                        )
                    }.let { response -> if (response.status.value != 200) null
                        else response.body<AuthToken>() }

                    BearerTokens(token!!.accessToken, token!!.refreshToken)

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
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }

        }
    }
}