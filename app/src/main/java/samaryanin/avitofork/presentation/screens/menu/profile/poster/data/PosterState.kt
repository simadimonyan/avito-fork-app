package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

/**
 * Модель верхнеуровневых категорий
 * @param id уникальный идентификатор категории
 * @param name название категории
 * @param subs список подкатегорий
 */
data class Category(
    val id: String,
    val name: String,
    val subs: Set<SubCategory>
)

/**
 * Модель подкатегорий
 * @param id уникальный идентификатор категории
 * @param name название категории
 * @param fields список характеристик объявления
 */
data class SubCategory(
    val id: String,
    val name: String,
    val fields: Set<CategoryField>
)

/**
 * Модель структур данных для характеристик категорий
 */
sealed class CategoryField {

    /**
     * Мета-тег для разделения полей на категории
     * @param key ключ
     * @param fields значение
     */
    data class MetaTag(val key: String, val fields: Set<CategoryField>) : CategoryField()

    /**
     * Поле для ввода текстовых данных категории: описание, название
     * @param key ключ
     * @param value значение
     */
    data class TextField(val key: String, val value: String) : CategoryField()

    /**
     * Поле для ввода числовых данных категории: площадь, стоимость, размеры, время
     * @param key ключ
     * @param value значение
     * @param unitMeasure единица измерения
     */
    data class NumberField(val key: String, val value: String, val unitMeasure: String) : CategoryField()

    /**
     * Поле для выбора типа данных: тип дома, вид работы
     * @param key ключ
     * @param value значение
     * @param options данные для выбора
     * @param isOnlyOneToChoose выбрать только один тип или несколько
     */
    data class DropdownField(val key: String, val value: String, val options: Set<String>, val isOnlyOneToChoose: Boolean) : CategoryField()

    /**
     * Поле для загрузки фотографий
     * @param key ключ
     * @param count максимальное значение фотографий
     */
    data class PhotoPickerField(val key: String, val count: Int) : CategoryField()

    /**
     * Поле для загрузки фотографий по категориям: фотографии гостинной, спальни
     * @param key ключ
     * @param options категория - максимальное значение фотографий
     */
    data class PhotoPickerByCategoryField(val key: String, val options: HashMap<String, Int>) : CategoryField()

    /**
     * Поле для определения местоположения: адрес дома
     * @param key ключ
     */
    data class LocationField(val key: String) : CategoryField()

}

