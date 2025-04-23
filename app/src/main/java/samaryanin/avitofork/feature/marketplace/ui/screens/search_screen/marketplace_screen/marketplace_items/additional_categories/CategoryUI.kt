package samaryanin.avitofork.feature.marketplace.ui.screens.search_screen.marketplace_screen.marketplace_items.additional_categories

import androidx.annotation.DrawableRes
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Category

data class CategoryUI(
    val id: String,
    val name: String,
    @DrawableRes val imageRes: Int,
)

fun Category.toCategoryUI(): CategoryUI {
    return CategoryUI(
        id = id,
        name = this.name,
        imageRes = when (this.name) {
            "Авто" -> R.drawable.auto
            "Недвижимость" -> R.drawable.deal
            "Услуги" -> R.drawable.service
            "Электроника" -> R.drawable.smartphone
            else -> R.drawable.testimg
        }
    )
}
