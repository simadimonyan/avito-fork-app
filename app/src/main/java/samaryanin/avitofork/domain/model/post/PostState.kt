package samaryanin.avitofork.domain.model.post

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class PostState(

    val category: String = "",

    val subcategory: String = "",

    val data: PostData = PostData()

)

@Serializable
@Immutable
data class PostData(

    val photos: List<String> = mutableListOf(),

    val price: String = "",

    val unit: String = "",

    val description: String = "",

    // ключи характеристик карточки товара
    val options: Map<String, String> = mutableMapOf(),

    val location: String = ""

)
