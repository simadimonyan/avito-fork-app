package samaryanin.avitofork.data.repository.network

import android.util.Log
import androidx.compose.runtime.Stable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.dimagor555.avito.dto.AdDto
import ru.dimagor555.avito.dto.CategoryDto
import ru.dimagor555.avito.request.GetAdsByIdsRequestDto
import ru.dimagor555.avito.request.GetFilteredAdsRequestDto
import samaryanin.avitofork.domain.model.favorites.Ad
import samaryanin.avitofork.domain.model.favorites.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Stable
class AdRepo @Inject constructor() {

    private val httpClient = HttpClient(CIO) {
        defaultRequest {
            url("https://194.54.159.160/api/v1/")
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

    suspend fun getAdById(
        adId: String
    ): Ad? = httpClient
        .post("ad/byIds") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(GetAdsByIdsRequestDto(adId))
        }
        .body<List<AdDto>>()
        .map { it.toDomain() }
        .firstOrNull()

    suspend fun getAdsByIds(
        adsIds: List<String>
    ): List<Ad> = httpClient
        .post("ad/byIds") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(GetAdsByIdsRequestDto(adsIds))
        }
        .body<List<AdDto>>()
        .map { it.toDomain() }

    suspend fun getFilteredAds(
        categoryIds: List<String>,
    ): List<Ad> = httpClient
        .post("ad/all") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(GetFilteredAdsRequestDto(categoryIds = categoryIds.ifEmpty { null }))
        }
        .body<List<AdDto>>()
        .map { it.toDomain() }


    suspend fun getAllCategories(): List<Category> = httpClient
        .get("ad/categories")
        .body<List<CategoryDto>>()
        .map { it.toDomain() }
}

private fun AdDto.toDomain(): Ad {
    return Ad(
        id = id,
        title = title,
        price = "${(price.amountMinor / 100)} RUB",
        address = address.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
    )
}

private fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        imageUrl = iconUrl.orEmpty(),
    )
}