package samaryanin.avitofork.feature.poster.data.repository

import androidx.compose.runtime.Stable
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import ru.dimagor555.avito.ad.domain.Money
import ru.dimagor555.avito.ad.request.CreateAdRequestDto
import ru.dimagor555.avito.category.domain.field.FieldData
import ru.dimagor555.avito.category.domain.field.FieldValue
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
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        setBody(CreateAdRequestDto(UUID.randomUUID().toString(), categoryId,mutableListOf<FieldValue>(
            FieldValue(
                fieldId = "base_title",
                fieldData = FieldData.StringValue(
                    value = title
                )
            ),
            FieldValue(
                fieldId = "base_price",
                fieldData = FieldData.MoneyValue(
                    amountMinor = price.amountMinor,
                    currency = price.currency
                )
            ),
            FieldValue(
                fieldId = "base_description",
                fieldData = FieldData.StringValue(
                    value = description
                )
            ),
            FieldValue(
                fieldId = "base_address",
                fieldData = FieldData.StringValue(
                    value = address
                )
            ),
            FieldValue(
                fieldId = "base_image_ids",
                fieldData = FieldData.ListValue(
                    items = images.map { it.toDomain() }
                )
            )
        )))
    }.let { response ->
        response.status.value == 200
    }

    private fun String.toDomain(): FieldData.StringValue {
        return FieldData.StringValue(value = this)
    }

}