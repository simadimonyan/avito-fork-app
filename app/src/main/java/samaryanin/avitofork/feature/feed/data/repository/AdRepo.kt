package samaryanin.avitofork.feature.feed.data.repository

import android.util.Log
import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.delay
import ru.dimagor555.avito.ad.dto.AdDto
import ru.dimagor555.avito.ad.request.GetAdsByIdsRequestDto
import ru.dimagor555.avito.ad.request.GetFilteredAdsRequestDto
import ru.dimagor555.avito.category.domain.field.FieldData
import ru.dimagor555.avito.category.dto.CategoryDto
import ru.dimagor555.avito.user.request.ToggleFavouriteRequestDto
import samaryanin.avitofork.core.network.KtorClient
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.favorites.domain.models.Category
import samaryanin.avitofork.feature.feed.domain.models.feed.SearchRequest
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

    suspend fun getUsersAd() = httpClient
        .get("ad/owner") {
        }.body<List<AdDto>>()
        .map { it.toDomain() }

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

//    suspend fun safePostAdFavourite(categoryIds: List<String>): List<Ad>? {
//        return retryOnTimeout {
//            httpClient
//                .post("ad/all") {
//                    header(HttpHeaders.ContentType, ContentType.Application.Json)
//                    setBody(GetFilteredAdsRequestDto(categoryIds = categoryIds.ifEmpty { null }))
//                }
//                .body<List<AdDto>>()
//                .map { it.toDomain() }
//            }
//        }


    suspend fun getSearchedAds(
        searchText: String
    ): List<Ad> = httpClient
        .post("ad/search") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(SearchRequest(query = searchText))

        }
        .body<List<AdDto>>()
        .map { it.toDomain() }

    suspend fun getFavoriteAds(): List<Ad> = httpClient
        .get("ad/favourite") {
        }
        .body<List<AdDto>>()
        .map { it.toDomain() }

    suspend fun toggleFavouriteAd(adId: String, isFavorite: Boolean) = httpClient
        .post("user/toggleFavouriteAd") {
            setBody(ToggleFavouriteRequestDto(adId = adId, isFavourite = isFavorite))
        }

    suspend fun getAllCategories(): List<Category> = httpClient
        .get("category/all")
        .body<List<CategoryDto>>()
        .map { it.toDomain() }

    suspend fun getImageBytesById(imageId: String): ByteArray = httpClient
        .get("images/$imageId")
        .body()

    private fun AdDto.toDomain(): Ad {

        var title = ""
        var price = ""
        var description = ""
        var address = ""
        var imageIds = mutableListOf<String>()

        for (field in fieldValues) {
            Log.d("AdRepo", "FieldId=${field.fieldId}, Data=${field.fieldData}")
            when (field.fieldId) {
                "base_title" -> title = (field.fieldData as? FieldData.StringValue)?.value.orEmpty()
                "base_price" -> price =
                    (field.fieldData as? FieldData.MoneyValue)?.amountMinor?.toString().orEmpty()

                "base_description" -> description =
                    (field.fieldData as? FieldData.StringValue)?.value.orEmpty()

                "base_address" -> address =
                    (field.fieldData as? FieldData.StringValue)?.value.orEmpty()

                "base_image_ids" -> {
                    val list = (field.fieldData as? FieldData.ListValue)?.items.orEmpty()
                    imageIds = list
                        .filterIsInstance<FieldData.StringValue>()
                        .map { it.value }
                        .toMutableList()
                }
            }
        }

        val ad = Ad(
            id = id,
            ownerId = ownerId,
            categoryId = categoryId,
            title = title,
            price = price,
            description = description,
            address = address,
            imageIds = imageIds
        )
        Log.d("AdRepo", "Ad=${ad}")

        return ad

    }

    private fun CategoryDto.toDomain(): Category {

        return Category(
            id = id,
            name = name,
            imageId = imageId.orEmpty(),
        )

    }

    private suspend inline fun <T> retryOnTimeout(
        retries: Int = 3,
        delayMillis: Long = 1000,
        block: () -> T
    ): T? {
        repeat(retries - 1) { attempt ->
            try {
                return block()
            } catch (e: SocketTimeoutException) {
                delay(delayMillis * (attempt + 1))
            }
        }
        return try {
            block()
        } catch (e: SocketTimeoutException) {
            null
        }
    }

}