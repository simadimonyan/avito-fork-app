package samaryanin.avitofork.domain.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostState(

    val category: String = "",

    val subcategory: String = "",

    val data: PostData = PostData()

)

@Serializable
data class PostData(

    val photos: List<String> = mutableListOf(),

    val price: String = "",

    val unit: String = "",

    val description: String = "",

    // ключи характеристик карточки товара
    val options: Map<String, String> = mutableMapOf(),

    val location: String = ""

)
