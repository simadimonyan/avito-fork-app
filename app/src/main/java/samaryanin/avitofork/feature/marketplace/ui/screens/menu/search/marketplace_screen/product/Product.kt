package samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.marketplace_screen.product

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val location: String,
    val imageUrl: String,
)