package samaryanin.avitofork.feature.poster.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Модель структур данных для характеристик категорий
 */
@Serializable
@Polymorphic
@Immutable
sealed class CategoryField {

    /**
     * Модель верхнеуровневых категорий
     * @param id уникальный идентификатор категории
     * @param name название категории
     * @param subs список подкатегорий
     */
    @Serializable
    @SerialName("category")
    @Immutable
    data class Category(val id: String = "", val name: String = "", val subs: List<SubCategory> = emptyList()) : CategoryField()

    /**
     * Модель подкатегорий
     * @param id уникальный идентификатор подкатегории
     * @param name название категории
     * @param fields список характеристик объявления
     */
    @Serializable
    @SerialName("subcategory")
    @Immutable
    data class SubCategory(val id: String, val name: String, val fields: List<CategoryField>) : CategoryField()

    /**
     * Мета-тег для разделения полей на категории
     * @param key ключ
     * @param fields значение
     */
    @Serializable
    @SerialName("meta-tag")
    @Immutable
    data class MetaTag(val key: String, val fields: List<CategoryField>) : CategoryField()

    /**
     * Поле для ввода текстовых данных категории: описание
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @SerialName("text-field")
    @Immutable
    data class TextField(val key: String, val value: String, val isRequired: Boolean = false) : CategoryField()

    /**
     * Поле для ввода текстовых данных заголовка
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @SerialName("title-field")
    @Immutable
    data class TitleField(val key: String, val value: String, val isRequired: Boolean = true) : CategoryField()

    /**
     * Поле для ввода данных описания
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @SerialName("description-field")
    @Immutable
    data class DescriptionField(val key: String,  val value: String, val isRequired: Boolean = true) : CategoryField()

    /**
     * Поле для ввода данных стоимости
     * @param key ключ
     * @param value значение
     * @param unitMeasure единица измерения
     */
    @Serializable
    @SerialName("price-field")
    @Immutable
    data class PriceField(val key: String, val value: String, val unitMeasure: String, val isRequired: Boolean = true) : CategoryField()

    /**
     * Поле для ввода числовых данных категории: площадь, стоимость, размеры, время
     * @param key ключ
     * @param value значение
     * @param unitMeasure единица измерения
     */
    @Serializable
    @SerialName("number-field")
    @Immutable
    data class NumberField(val key: String, val value: String, val unitMeasure: String, val isRequired: Boolean = false) : CategoryField()

    /**
     * Поле для выбора типа данных: тип дома, вид работы
     * @param key ключ
     * @param value значение
     * @param options данные для выбора
     * @param isOnlyOneToChoose выбрать только один тип или несколько
     */
    @Serializable
    @SerialName("dropdown-field")
    @Immutable
    data class DropdownField(val key: String, val value: String, val options: List<String>, val isOnlyOneToChoose: Boolean, val isRequired: Boolean = false) : CategoryField()

    /**
     * Поле для загрузки фотографий
     * @param key ключ
     * @param count максимальное значение фотографий
     */
    @Serializable
    @SerialName("photo-picker-field")
    @Immutable
    data class PhotoPickerField(val key: String, val count: Int, val isRequired: Boolean = true) : CategoryField()

    /**
     * Поле для загрузки фотографий по категориям: фотографии гостинной, спальни
     * @param key ключ
     * @param options категория - максимальное значение фотографий
     */
    @Serializable
    @SerialName("photo-picker-by-category-field")
    @Immutable
    data class PhotoPickerByCategoryField(val key: String, val options: HashMap<String, Int>, val isRequired: Boolean = false) : CategoryField()

    /**
     * Поле для определения местоположения: адрес дома
     * @param key ключ
     */
    @Serializable
    @SerialName("location-field")
    @Immutable
    data class LocationField(val key: String, val isRequired: Boolean = false) : CategoryField()

}

