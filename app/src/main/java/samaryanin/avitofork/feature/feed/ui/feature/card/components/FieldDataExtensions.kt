package samaryanin.avitofork.feature.feed.ui.feature.card.components

import ru.dimagor555.avito.category.domain.field.FieldData

fun FieldData.toDisplayString(fieldId: String? = null): String {
    val rawValue = when (this) {
        is FieldData.StringValue -> value
        is FieldData.IntValue -> value.toString()
        is FieldData.DoubleValue -> value.toString()
        is FieldData.BoolValue -> if (value) "Да" else "Нет"
        is FieldData.MoneyValue -> "${(amountMinor / 100)} $currency"
        is FieldData.AddressValue -> fullText
        is FieldData.ListValue -> items.joinToString(", ") { it.toDisplayString() }
    }

    val suffix = when (fieldId) {
        "transport_power_hp" -> " л.с."
        "transport_mileage" -> " км"
        "transport_year" -> " г."
        else -> ""
    }

    return rawValue + suffix
}