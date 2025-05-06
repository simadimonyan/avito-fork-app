package samaryanin.avitofork.feature.poster.data.repository

import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import ru.dimagor555.avito.ad.dto.MoneyDto
import ru.dimagor555.avito.ad.request.CreateAdRequestDto
import samaryanin.avitofork.core.network.KtorClient
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class PosterRepository @Inject constructor(
    ktorClient: KtorClient
) {

    private val httpClient = ktorClient.httpClient
    private val strictUrl: String = "/api/v1"

    suspend fun create(
        title: String,
        description: String,
        images: List<String>,
        price: MoneyDto,
        address: String,
        categoryId: String
    ): PostState = httpClient.post("$strictUrl/ad/create") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        setBody(CreateAdRequestDto(UUID.randomUUID().toString(), title, description, images, price, address, categoryId))
    }.let { response ->
        if (response.status.value == 200) {

            val responseDto = response.body<CreateAdRequestDto>()

            val ids = mutableMapOf<Int, String>()

            responseDto.imageIds.forEach { id ->
                ids.put(responseDto.imageIds.indexOf(id), id)
            }

            PostState(
                category = responseDto.categoryId,
                data = PostData(
                    name = responseDto.title,
                    description = responseDto.description.toString(),
                    photos = ids,
                    price = responseDto.price!!.amountMinor.toString(),
                    unit = responseDto.price!!.currency.name,
                    location = responseDto.address.toString()
                )
            )

        }
        else
            PostState()
    }

}