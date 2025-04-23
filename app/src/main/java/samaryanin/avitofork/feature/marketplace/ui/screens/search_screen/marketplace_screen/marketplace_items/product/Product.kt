package samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen.marketplace_items.product

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val location: String,
    val imageUrl: String,
)