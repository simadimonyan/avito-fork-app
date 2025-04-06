package samaryanin.avitofork.domain.model

data class Ad(
    val id: String,
    val title: String = "",
    val price: String = "",
    val address: String = "",
    val imageUrl: String? = null
)