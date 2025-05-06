package samaryanin.avitofork.feature.feed.ui.feature.card.components

import androidx.annotation.DrawableRes
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.favorites.domain.models.Category

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
