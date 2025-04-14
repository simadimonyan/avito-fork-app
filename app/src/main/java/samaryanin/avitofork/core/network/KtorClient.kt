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
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.dimagor555.avito.auth.request.RefreshRequestDto
import samaryanin.avitofork.core.utils.DeviceIdProvider
import samaryanin.avitofork.feature.auth.data.dto.AuthToken
import javax.inject.Inject

class KtorClient @Inject constructor(
    private val context: Context,
    private val baseUrl: String,
) {

    private val gson = Gson()

    val httpClient = HttpClient(CIO) {

        defaultRequest {
            url {
                takeFrom(baseUrl)
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

        install(DefaultRequest) {
            val deviceId = DeviceIdProvider.getDeviceId(context)
            header("device-id", deviceId)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            // токен авторизации
          //  val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
         //   val token = prefs.getString("access_token", null)
            val token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2FwaSIsImxvZ2luVHlwZSI6IlVTRVIiLCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgwLyIsImlkIjoiNTE4OGZiYjUtYjBmOC00MjljLTk4YWEtODg5ODMzMjJmMjQ3IiwiZXhwIjoxNzQ0NTQ2MjIwLCJpYXQiOjE3NDQyODcwMjB9.HVJsQ6Z74fWcQWcQgAQw5niLiOjhsg_ZSmM017NOCTck6tWrCgEMcXkQGZD5WpdIjU2ATrfpVNPgz1X0pfUsN3jmaj4wBUVxQhCS-yhKUJiNmE1k6SsbVL3LGcbcnjFuVYM0AYuB-2kuRku9_oB0Q0Mrp4Gf08naNL8T1eKGJDaNqcgj7jwa4I_Ht_S5IDxUT76jfTpvPrG8PSWVF-lrDh6mEMfQJ35dXW6rSu5p6oPHfqlsuWoUfz23OSODmAYvBomhMtQ218tYQqUSlTOlIafQTpasG1dT4QjNd8CpZHl5vOkwq7iDMiRah9zSLihJYNzzl79AgpolrA05_sFYtA"
            if (!token.isNullOrBlank()) {
                header(HttpHeaders.Authorization, "Bearer $token")
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

                    val prefs = context.getSharedPreferences("encrypted_prefs", Context.MODE_PRIVATE)
                    val json = prefs.getString("authToken", null)

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

                    val token = this.client.post("$baseUrl/auth/refresh") {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                        setBody(
                            RefreshRequestDto(
                                oldTokens!!.accessToken,
                                oldTokens!!.refreshToken!!
                            )
                        )
                    }.let { response -> if (response.status.value != 200) null
                        else response.body<AuthToken>() }

                    BearerTokens(token!!.accessToken, token.refreshToken)

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