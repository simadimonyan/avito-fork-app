package samaryanin.avitofork.feature.poster.domain.usecases

import android.util.Log
import androidx.compose.runtime.Immutable
import ru.dimagor555.avito.category.domain.Category
import ru.dimagor555.avito.category.domain.field.DataType
import ru.dimagor555.avito.category.domain.field.FieldDefinition
import ru.dimagor555.avito.category.domain.tree.CategoryTreeMapper
import samaryanin.avitofork.feature.poster.data.repository.CategoryRepository
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
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
                    Log.e("ConfigurationUseCase", "Parent not found for ${node.name}")
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
        val fields = mutableListOf<CategoryField>(
            CategoryField.MetaTag(
                "null",
                "",
                "",
                mutableListOf(
                    CategoryField.PhotoPickerField("base_image_ids", "list_value",  "", 8),
                    CategoryField.TitleField("base_title", "string_value", "Название", "", true),
                    CategoryField.PriceField("base_price", "money_value", "Стоимость", "", "руб"),
                    CategoryField.DescriptionField("base_description", "string_value", "Описание:", "")
                ),
            )
        )

        val allFieldDefinitions = collectAllFields(child)

        val categoryParams = allFieldDefinitions.mapNotNull { field ->
            when (field.semantic.dataType) {
                is DataType.Text -> CategoryField.TextField(field.id, "string_value", field.name, "", field.isRequired)
                is DataType.DoubleNumber, is DataType.IntNumber -> CategoryField.NumberField(field.id, "int_value", field.name, "", "", field.isRequired)
                is DataType.SingleOption -> CategoryField.DropdownField(field.id, "list_value", field.name,"", (field.semantic.dataType as DataType.SingleOption).options, true, field.isRequired)
                else -> null
            }
        }

        if (categoryParams.isNotEmpty())
            fields.add(CategoryField.MetaTag("null", "", "Характеристики категории", categoryParams.toMutableList()))

        return CategoryField.SubCategory(
            id = child.id,
            name = child.name,
            imageId = child.imageId.orEmpty(),
            fields = fields
        )
    }

    // сборка всех полей у родительских категорий
    private fun collectAllFields(category: Category): List<FieldDefinition> {
        val allFields = mutableListOf<FieldDefinition>()
        val seen = mutableSetOf<String>()
        var current: Category? = category

        while (current != null) {
            if (current.id != "root") {
                current.ownFieldDefinitions.reversed().forEach { field ->
                    if (field.semantic.id !in seen) {
                        allFields.add(0, field)
                        seen.add(field.semantic.id)
                    }
                }
            }
            current = current.parent
        }

        return allFields
    }

}