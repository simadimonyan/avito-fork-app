package samaryanin.avitofork.feature.poster.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

interface ApiForm {
    val baseId: String
    val semanticType: String
}

/**
 * Модель структур данных для характеристик категорий
 */
@Serializable
@Polymorphic
@Immutable
sealed class CategoryField : ApiForm {

    /**
     * Модель верхнеуровневых категорий
     * @param id уникальный идентификатор категории
     * @param name название категории
     * @param subs список подкатегорий
     */
    @Serializable
    @Immutable
    data class Category(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val id: String = "",
        val name: String = "",
        val imageId: String = "",
        var subs: MutableList<SubCategory> = mutableListOf()
    ) : CategoryField()

    /**
     * Модель подкатегорий
     * @param id уникальный идентификатор подкатегории
     * @param name название категории
     * @param children список подподкатегорий
     * @param fields список характеристик объявления
     */
    @Serializable
    @Immutable
    data class SubCategory(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val id: String,
        val name: String,
        val imageId: String = "",
        val fields: List<CategoryField>,
        var children: List<SubCategory> = emptyList()
    ) : CategoryField()

    /**
     * Мета-тег для разделения полей на категории
     * @param key ключ
     * @param fields значение
     */
    @Serializable
    @Immutable
    data class MetaTag(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val fields: List<CategoryField>
    ) : CategoryField()

    /**
     * Поле для ввода текстовых данных категории: описание
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @Immutable
    data class TextField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val value: String,
        val isRequired: Boolean = false
    ) : CategoryField()

    /**
     * Поле для ввода текстовых данных заголовка
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @Immutable
    data class TitleField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val value: String,
        val isRequired: Boolean = true
    ) : CategoryField()

    /**
     * Поле для ввода данных описания
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @Immutable
    data class DescriptionField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val value: String,
        val isRequired: Boolean = true
    ) : CategoryField()

    /**
     * Поле для ввода данных стоимости
     * @param key ключ
     * @param value значение
     * @param unitMeasure единица измерения
     */
    @Serializable
    @Immutable
    data class PriceField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val value: String,
        val unitMeasure: String,
        val isRequired: Boolean = true
    ) : CategoryField()

    /**
     * Поле для ввода числовых данных категории: площадь, стоимость, размеры, время
     * @param key ключ
     * @param value значение
     * @param unitMeasure единица измерения
     */
    @Serializable
    @Immutable
    data class NumberField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val value: String,
        val unitMeasure: String,
        val isRequired: Boolean = false
    ) : CategoryField()

    /**
     * Поле для выбора типа данных: тип дома, вид работы
     * @param key ключ
     * @param value значение
     * @param options данные для выбора
     * @param isOnlyOneToChoose выбрать только один тип или несколько
     */
    @Serializable
    @Immutable
    data class DropdownField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val value: String = "Не выбрано",
        val options: List<String>,
        val isOnlyOneToChoose: Boolean,
        val isRequired: Boolean = false
    ) : CategoryField()

    /**
     * Поле для загрузки фотографий
     * @param key ключ
     * @param count максимальное значение фотографий
     */
    @Serializable
    @Immutable
    data class PhotoPickerField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val count: Int,
        val isRequired: Boolean = true
    ) : CategoryField()

    /**
     * Поле для загрузки фотографий по категориям: фотографии гостинной, спальни
     * @param key ключ
     * @param options категория - максимальное значение фотографий
     */
    @Serializable
    @Immutable
    data class PhotoPickerByCategoryField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val options: HashMap<String, Int>,
        val isRequired: Boolean = false
    ) : CategoryField()

    /**
     * Поле для определения местоположения: адрес дома
     * @param key ключ
     */
    @Serializable
    @Immutable
    data class LocationField(
        override val baseId: String = "null",
        override val semanticType: String = "null",

        val key: String,
        val isRequired: Boolean = false
    ) : CategoryField()

}



