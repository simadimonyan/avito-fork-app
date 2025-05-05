package samaryanin.avitofork.feature.feed.domain.models.feed

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val location: String,
    val imageUrl: String,
)