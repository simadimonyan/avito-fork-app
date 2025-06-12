package samaryanin.avitofork.feature.poster.data.repository

import androidx.compose.runtime.Stable
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import ru.dimagor555.avito.ad.domain.Money
import ru.dimagor555.avito.ad.request.CreateAdRequestDto
import samaryanin.avitofork.core.network.KtorClient
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
        price: Money,
        address: String,
        categoryId: String
    ): Boolean = httpClient.post("$strictUrl/ad/create") {
        header(HttpHeaders.ContentType, ContentType.Application.Json) //TODO
        //setBody(CreateAdRequestDto(UUID.randomUUID().toString(), title, description, images, price, address, categoryId))
    }.let { response ->
        response.status.value == 200
    }

}