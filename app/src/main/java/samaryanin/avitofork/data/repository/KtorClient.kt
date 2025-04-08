package samaryanin.avitofork.data.repository

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient(url: String) {

    val httpClient = HttpClient(CIO) {
        defaultRequest {
            url(url)
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