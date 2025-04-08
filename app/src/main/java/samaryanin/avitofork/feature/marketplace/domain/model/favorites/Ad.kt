package samaryanin.avitofork.feature.marketplace.domain.model.favorites

data class Ad(
    val id: String,
    val title: String = "",
    val description: String?,
    val price: String = "",
    val address: String = "",
    val imageUrl: String? = null
)