package samaryanin.avitofork.domain.model.post

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Модель структур данных для характеристик категорий
 */
@Serializable
@Polymorphic
sealed class CategoryField {

    /**
     * Модель верхнеуровневых категорий
     * @param id уникальный идентификатор категории
     * @param name название категории
     * @param subs список подкатегорий
     */
    @Serializable
    @SerialName("category")
    data class Category(val id: String = "", val name: String = "", val subs: List<SubCategory> = emptyList()) : CategoryField()

    /**
     * Модель подкатегорий
     * @param id уникальный идентификатор подкатегории
     * @param name название категории
     * @param fields список характеристик объявления
     */
    @Serializable
    @SerialName("subcategory")
    data class SubCategory(val id: String, val name: String, val fields: List<CategoryField>) : CategoryField()

    /**
     * Мета-тег для разделения полей на категории
     * @param key ключ
     * @param fields значение
     */
    @Serializable
    @SerialName("meta-tag")
    data class MetaTag(val key: String, val fields: List<CategoryField>) : CategoryField()

    /**
     * Поле для ввода текстовых данных категории: описание, название
     * @param key ключ
     * @param value значение
     */
    @Serializable
    @SerialName("text-field")
    data class TextField(val key: String, val value: String) : CategoryField()

    /**
     * Поле для ввода числовых данных категории: площадь, стоимость, размеры, время
     * @param key ключ
     * @param value значение
     * @param unitMeasure единица измерения
     */
    @Serializable
    @SerialName("number-field")
    data class NumberField(val key: String, val value: String, val unitMeasure: String) : CategoryField()

    /**
     * Поле для выбора типа данных: тип дома, вид работы
     * @param key ключ
     * @param value значение
     * @param options данные для выбора
     * @param isOnlyOneToChoose выбрать только один тип или несколько
     */
    @Serializable
    @SerialName("dropdown-field")
    data class DropdownField(val key: String, val value: String, val options: List<String>, val isOnlyOneToChoose: Boolean) : CategoryField()

    /**
     * Поле для загрузки фотографий
     * @param key ключ
     * @param count максимальное значение фотографий
     */
    @Serializable
    @SerialName("photo-picker-field")
    data class PhotoPickerField(val key: String, val count: Int) : CategoryField()

    /**
     * Поле для загрузки фотографий по категориям: фотографии гостинной, спальни
     * @param key ключ
     * @param options категория - максимальное значение фотографий
     */
    @Serializable
    @SerialName("photo-picker-by-category-field")
    data class PhotoPickerByCategoryField(val key: String, val options: HashMap<String, Int>) : CategoryField()

    /**
     * Поле для определения местоположения: адрес дома
     * @param key ключ
     */
    @Serializable
    @SerialName("location-field")
    data class LocationField(val key: String) : CategoryField()

}

