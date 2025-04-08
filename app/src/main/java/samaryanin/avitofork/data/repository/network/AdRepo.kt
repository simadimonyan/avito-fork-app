package samaryanin.avitofork.data.repository.network

import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import ru.dimagor555.avito.dto.AdDto
import ru.dimagor555.avito.dto.CategoryDto
import ru.dimagor555.avito.request.GetAdsByIdsRequestDto
import ru.dimagor555.avito.request.GetFilteredAdsRequestDto
import samaryanin.avitofork.data.repository.KtorClient
import samaryanin.avitofork.domain.model.favorites.Ad
import samaryanin.avitofork.domain.model.favorites.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Stable
class AdRepo @Inject constructor(
    ktorClient: KtorClient
) {

    private val httpClient = ktorClient.httpClient

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
        description = description.orEmpty()
    )

}

private fun CategoryDto.toDomain(): Category {

    return Category(
        id = id,
        name = name,
        imageUrl = iconUrl.orEmpty(),
    )

}