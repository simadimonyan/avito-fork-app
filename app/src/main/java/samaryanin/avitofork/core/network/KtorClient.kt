package samaryanin.avitofork.core.network

import android.content.Context
import android.provider.Settings
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import samaryanin.avitofork.core.utils.DeviceIdProvider
import javax.inject.Inject

class KtorClient @Inject constructor(
    private val context: Context,
    private val baseUrl: String
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

            // токен авторизации
//            val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
//            val token = prefs.getString("access_token", null)
//            if (!token.isNullOrBlank()) {
//                header(HttpHeaders.Authorization, "Bearer $token")
//            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
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