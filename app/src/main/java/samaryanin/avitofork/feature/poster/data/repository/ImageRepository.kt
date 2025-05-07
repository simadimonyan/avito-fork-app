package samaryanin.avitofork.feature.poster.data.repository

import android.util.Log
import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import ru.dimagor555.avito.image.dto.UploadImageTypeDto
import samaryanin.avitofork.core.network.KtorClient
import samaryanin.avitofork.feature.poster.data.models.ImageDto
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class ImageRepository @Inject constructor(
    ktorClient: KtorClient
) {

    private val httpClient = ktorClient.httpClient
    private val strictUrl: String = "/api/v1"

    suspend fun upload(
        file: File,
        imageTypeDto: UploadImageTypeDto
    ): ImageDto = httpClient.submitFormWithBinaryData(
        url = "$strictUrl/uploadImage",
        formData = formData {
            append("params", "{ imageType: ${imageTypeDto.name} }", Headers.build {
                append(HttpHeaders.ContentType, "application/json")
            })
            append("file", file.readBytes(),  Headers.build {
                append(HttpHeaders.ContentType, "image/png")
                append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
            })
        }
    ).let { response ->

        if (response.status.value == 200) {
            return response.body<ImageDto>()
        }
        else
            ImageDto()
    }

}