package samaryanin.avitofork.feature.favorites.domain.models

data class Ad(
    val id: String,
    val title: String = "",
    val description: String?,
    val price: String = "",
    val address: String = "",
    val imageIds: List<String>,
)