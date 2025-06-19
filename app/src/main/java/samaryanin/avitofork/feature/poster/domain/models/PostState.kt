package samaryanin.avitofork.feature.poster.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class PostState(

    // может быть подкатегорией
    val categoryName: String = "",

    // может быть подкатегорией
    val categoryId: String = "",

    val data: PostData = PostData(),

    val timestamp: String = ""

)

@Serializable
@Immutable
data class PostData(

    val name: String = "",

    val photos: MutableMap<Int, String> = mutableMapOf<Int, String>(),

    val price: String = "",

    val unit: String = "",

    val description: String = "",

    // ключи характеристик карточки товара
    val options: Map<String, String> = mutableMapOf(),

    val location: String = "test"

)
