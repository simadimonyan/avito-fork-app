package samaryanin.avitofork.feature.favorites.domain.models

import ru.dimagor555.avito.category.domain.field.FieldValue

data class Ad(
    val id: String,
    val ownerId: String = "",
    val categoryId: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val address: String = "",
    val imageIds: List<String>,
    val fieldValues: List<FieldValue>,
    )