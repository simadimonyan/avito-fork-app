package samaryanin.avitofork.feature.poster.domain.usecases

import android.util.Log
import androidx.compose.runtime.Immutable
import ru.dimagor555.avito.ad.domain.Currency
import ru.dimagor555.avito.ad.domain.Money
import ru.dimagor555.avito.category.domain.field.FieldData
import ru.dimagor555.avito.category.domain.field.FieldValue
import samaryanin.avitofork.feature.poster.data.repository.PosterRepository
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.domain.models.PostState
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
@Singleton
class CreatePostUseCase @Inject constructor(
    private val posterRepository: PosterRepository
) {

    suspend fun create(state: PostState): Boolean {
        return try {

            val price = Money(
                if (state.data.price.contains('.')) {
                    val parts = state.data.price.split('.')
                    val major = parts[0]
                    val minor = (parts.getOrNull(1) ?: "00").padEnd(2, '0').take(2)
                    (major + minor).toLong()
                } else {
                    (state.data.price + "00").toLong()
                },
                Currency.RUB
            )

            val images = state.data.photos.values.toList()

            // базовые поля root
            val fields = mutableListOf(
                FieldValue(
                    fieldId = "base_title",
                    fieldData = FieldData.StringValue(
                        value = state.data.name
                    )
                ),
                FieldValue(
                    fieldId = "base_price",
                    fieldData = FieldData.MoneyValue(
                        amountMinor = price.amountMinor,
                        currency = price.currency
                    )
                ),
                FieldValue(
                    fieldId = "base_description",
                    fieldData = FieldData.StringValue(
                        value = state.data.description
                    )
                ),
                FieldValue(
                    fieldId = "base_address",
                    fieldData = FieldData.StringValue(
                        value = state.data.location
                    )
                ),
                FieldValue(
                    fieldId = "base_image_ids",
                    fieldData = FieldData.ListValue(
                        items = images.map { it.toDomain() }
                    )
                )
            )

            // все остальные поля
            val formFields = state.data.options.values.mapNotNull { field ->
                if (field.baseId != "null") {
                    when (field) {
                        is CategoryField.TextField -> FieldValue(
                            fieldId = field.baseId,
                            fieldData = FieldData.StringValue(
                                value = field.value
                            )
                        )
                        is CategoryField.NumberField -> {
                            when (field.semanticType) {
                                "int_value" -> FieldValue(
                                    fieldId = field.baseId,
                                    fieldData = FieldData.IntValue(
                                        value = field.value.toInt()
                                    )
                                )
                                "double_value" -> FieldValue(
                                    fieldId = field.baseId,
                                    fieldData = FieldData.DoubleValue(
                                        value = field.value.toDouble()
                                    )
                                )
                                else -> null
                            }
                        }
                        is CategoryField.DropdownField -> FieldValue(
                            fieldId = field.baseId,
                            fieldData = FieldData.ListValue(
                                items = listOf(FieldData.StringValue(field.value))
                            )
                        )
                        else -> null
                    }
                } else null
            }

            Log.d("fields1: ", fields.toString())

            fields.addAll(formFields)

            Log.d("fields2: ", fields.toString())

            posterRepository.create(
                fields,
                state.categoryId
            )
        }
        catch (e: Exception) {
            Log.d("create", e.toString())
            false
        }
    }

    private fun String.toDomain(): FieldData.StringValue {
        return FieldData.StringValue(value = this)
    }

}