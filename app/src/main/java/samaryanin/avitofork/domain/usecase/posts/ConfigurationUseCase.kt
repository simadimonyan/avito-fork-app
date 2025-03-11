package samaryanin.avitofork.domain.usecase.posts

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import samaryanin.avitofork.data.json.CategoryDeserializer
import samaryanin.avitofork.domain.model.post.CategoryField
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigurationUseCase @Inject constructor() {

    private val prompt = """
        {
          "name": "Автомобили",
          "subs": [
            {
              "name": "Легковые автомобили",
              "fields": [
                {
                  "type": "TextField",
                  "key": "make",
                  "value": "Toyota"
                },
                {
                  "type": "NumberField",
                  "key": "year",
                  "value": "",
                  "unitMeasure": "год"
                },
                {
                  "type": "DropdownField",
                  "key": "fuel",
                  "value": "Бензин",
                  "options": ["Бензин", "Дизель", "Электро"],
                  "isOnlyOneToChoose": true
                },
                {
                  "type": "PhotoPickerField",
                  "key": "photos",
                  "count": 5
                }
              ]
            }
          ]
        }
    """

    fun getCategories(): List<CategoryField> {

        val gson = GsonBuilder()
            .registerTypeAdapter(CategoryField::class.java, CategoryDeserializer())
            .create()

        val listType = object : TypeToken<List<CategoryField>>() {}.type
        val categoryFields: List<CategoryField> = gson.fromJson(prompt, listType)

        return categoryFields
    }

}