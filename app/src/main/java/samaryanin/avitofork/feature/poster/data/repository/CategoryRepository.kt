package samaryanin.avitofork.feature.poster.data.repository

import androidx.compose.runtime.Stable
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.dimagor555.avito.category.dto.CategoryDto
import samaryanin.avitofork.core.network.KtorClient
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class CategoryRepository @Inject constructor(
    private val ktorClient: KtorClient
) {

    private val httpClient = ktorClient.httpClient

    suspend fun getAllCategories(): List<CategoryDto> = httpClient
        .get("category/all")
        .body<List<CategoryDto>>()

}