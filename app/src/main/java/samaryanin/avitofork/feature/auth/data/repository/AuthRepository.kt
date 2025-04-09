package samaryanin.avitofork.feature.auth.data.repository

import android.provider.Settings
import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import ru.dimagor555.avito.auth.request.LoginRequestDto
import ru.dimagor555.avito.auth.request.RefreshRequestDto
import ru.dimagor555.avito.auth.request.RegistrationRequestDto
import ru.dimagor555.avito.auth.request.VerityCodeRequestDto
import samaryanin.avitofork.core.network.KtorClient
import samaryanin.avitofork.feature.auth.data.dto.AuthToken
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class AuthRepository @Inject constructor(
    ktorClient: KtorClient,
) {

    private val httpClient = ktorClient.httpClient

    suspend fun register(email: String, password: String, name: String): Boolean =
        httpClient.post("/auth/register") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append("device-id", Settings.Secure.ANDROID_ID)
            }
            setBody(RegistrationRequestDto(email, password, name))
        }.let { response -> response.status.value == 200 }

    suspend fun verify(email: String, code: String): AuthToken? {
        return try {
            httpClient.post("/auth/verify") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                headers {
                    append("device-id", Settings.Secure.ANDROID_ID)
                }
                setBody(VerityCodeRequestDto(email, code))
            }.let { response ->
                if (response.status.value != 200) null
                else response.body<AuthToken>()
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun login(email: String, password: String): AuthToken?  =
        httpClient.post("/auth/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append("device-id", Settings.Secure.ANDROID_ID)
            }
            setBody(LoginRequestDto(email, password))
        }.let { response -> if (response.status.value != 200) null
            else response.body<AuthToken>() }

    suspend fun refresh(oldAccessToken: String, oldRefreshToken: String): AuthToken? =
        httpClient.post("/auth/refresh") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append("device-id", Settings.Secure.ANDROID_ID)
            }
            setBody(RefreshRequestDto(oldRefreshToken, oldRefreshToken))
        }.let { response -> if (response.status.value != 200) null
            else response.body<AuthToken>() }

}