package samaryanin.avitofork.feature.marketplace.data.repository.ad

import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import ru.dimagor555.avito.ad.dto.AdDto
import ru.dimagor555.avito.ad.dto.CategoryDto
import ru.dimagor555.avito.ad.request.GetAdsByIdsRequestDto
import ru.dimagor555.avito.ad.request.GetFilteredAdsRequestDto
import ru.dimagor555.avito.user.request.ToggleFavouriteRequestDto
import samaryanin.avitofork.core.network.KtorClient
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Category
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

    suspend fun getFavoriteAds(): List<Ad> = httpClient
        .get("ad/favourite") {
        }
        .body<List<AdDto>>()
        .map { it.toDomain() }

    suspend fun toggleFavouriteAd(adId: String, isFavorite: Boolean) = httpClient
        .post("user/toggleFavouriteAd"){
            setBody(ToggleFavouriteRequestDto(adId = adId, isFavourite = isFavorite))
        }

    suspend fun getAllCategories(): List<Category> = httpClient
        .get("ad/categories")
        .body<List<CategoryDto>>()
        .map { it.toDomain() }

    suspend fun getImageBytesById(imageId: String): ByteArray = httpClient
        .get("images/$imageId")
        .body()
}


private fun AdDto.toDomain(): Ad {

    return Ad(
        id = id,
        title = title,
        price = "${(price!!.amountMinor / 100)} RUB",
        address = address.orEmpty(),
        imageIds = imageIds.orEmpty(),
        description = description.orEmpty()
    )

}

private fun CategoryDto.toDomain(): Category {

    return Category(
        id = id,
        name = name,
        imageId = imageId.orEmpty(),
    )

}