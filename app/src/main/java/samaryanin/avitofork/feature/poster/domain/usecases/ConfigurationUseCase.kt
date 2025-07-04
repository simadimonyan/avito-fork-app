package samaryanin.avitofork.feature.poster.domain.usecases

import androidx.compose.runtime.Immutable
import ru.dimagor555.avito.category.domain.Category
import ru.dimagor555.avito.category.domain.field.DataType
import ru.dimagor555.avito.category.domain.field.FieldDefinition
import ru.dimagor555.avito.category.domain.tree.CategoryTree
import ru.dimagor555.avito.category.domain.tree.CategoryTreeMapper
import samaryanin.avitofork.feature.poster.data.repository.CategoryRepository
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.DescriptionField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.DropdownField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.LocationField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.NumberField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.PhotoPickerField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.PriceField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.TextField
import samaryanin.avitofork.feature.poster.domain.models.CategoryField.TitleField
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class ConfigurationUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend fun getCategories(): List<CategoryField> {
        val serverCategories = categoryRepository.getAllCategories()
        val treeMap = CategoryTreeMapper(serverCategories).map()

        val categoryFields = mutableListOf<CategoryField.Category>()

        // проход по всем узлам: сначала — корневые
        treeMap.all().forEach { node ->
            if (node.isFirstLevelCategory) {
                categoryFields += CategoryField.Category(
                    id = node.id,
                    name = node.name,
                    imageId = node.imageId.orEmpty(),
                    subs = mutableListOf()
                )
            }
        }

        // проход по всем узлам: затем — субкатегории
        treeMap.all().filter { it.isSubcategory }.forEach { node ->
            val parentNode = categoryFields.firstOrNull { it.id == node.parent?.id }
                ?: run {
                    //Log.e("ConfigurationUseCase", "Parent not found for ${node.name}")
                    return@forEach
                }

            val sub = if (node.hasChildren) {
                // Подкатегория с дочерними SubCategory
                val childrenSubs = node.children.map { child ->
                    formFields(child)
                }
                CategoryField.SubCategory(
                    id = node.id,
                    name = node.name,
                    imageId = node.imageId.orEmpty(),
                    children = childrenSubs,
                    fields = emptyList()
                )
            } else {
                // если конечная подкатегория — сразу поля
                formFields(node)
            }

            parentNode.subs.add(sub)
        }

        return categoryFields
    }

    // маппинг полей в рендерные блоки
    private fun formFields(child: Category): CategoryField.SubCategory {
        val fields = mutableListOf<CategoryField>()

        val allFieldDefinitions = collectAllFields(child)

        val bases: List<CategoryField> = allFieldDefinitions.mapNotNull { field ->
            if (field.semantic.id.contains("base")) {
                when(field.semantic.id) {
                    "base_image_ids" -> PhotoPickerField("base_image_ids", "list_value",  "", 8)
                    "base_title" -> TitleField("base_title", "string_value", "Название", "", true)
                    "base_price" -> PriceField("base_price", "money_value", "Стоимость", "", "руб")
                    "base_description" -> DescriptionField("base_description", "string_value", "Описание:", "")
                    "base_address" -> LocationField("base_address", "string_value", "Местоположение", true)
                    else -> null
                }
            } else null
        }

        val categoryParams: List<CategoryField> = allFieldDefinitions.mapNotNull { field ->
            if (!field.semantic.id.contains("base")) {
                when (field.semantic.dataType) {
                    is DataType.Text -> TextField(field.id, "string_value", field.name, "", field.isRequired)
                    is DataType.IntNumber -> NumberField(field.id, "int_value", field.name, "", "", field.isRequired)
                    is DataType.DoubleNumber -> NumberField(field.id, "double_value", field.name, "", "", field.isRequired)
                    is DataType.SingleOption -> DropdownField(field.id, "list_value", field.name, "", (field.semantic.dataType as DataType.SingleOption).options, true, field.isRequired)
                    else -> null
                }
            } else null
        }

        if (categoryParams.isNotEmpty()) {
            fields.add(
                CategoryField.MetaTag(
                    "null",
                    "",
                    "",
                    bases
                )
            )
            fields.add(
                CategoryField.MetaTag(
                    "null",
                    "",
                    "Характеристики категории",
                    categoryParams
                )
            )
        }

        return CategoryField.SubCategory(
            id = child.id,
            name = child.name,
            imageId = child.imageId.orEmpty(),
            fields = fields
        )
    }

    private var cachedCategoryTree: CategoryTree? = null

    suspend fun getCategoryTree(): CategoryTree {
        // Если дерево уже загружено, возвращаем кэш
        cachedCategoryTree?.let { return it }

        // Загружаем категории с сервера
        val serverCategories = categoryRepository.getAllCategories()

        // Собираем CategoryTree
        val categoryTree = CategoryTreeMapper(serverCategories).map()

        // Кэшируем
        cachedCategoryTree = categoryTree

        return categoryTree
    }


    // сборка всех полей у родительских категорий
    private fun collectAllFields(category: Category): List<FieldDefinition> {
        val allFields = mutableListOf<FieldDefinition>()
        val seen = mutableSetOf<String>()
        var current: Category? = category

        while (current != null) {
            current.ownFieldDefinitions.reversed().forEach { field ->
                if (field.semantic.id !in seen) {
                    allFields.add(field)
                    seen.add(field.semantic.id)
                }
            }
            current = current.parent
        }

        return allFields.sortedBy { it.priority }
    }

}