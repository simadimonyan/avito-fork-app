package samaryanin.avitofork.feature.poster.domain.usecases

import androidx.compose.runtime.Immutable
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.poster.data.repository.CategoryRepository
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class ConfigurationUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    private val prompt = """
        [
          {
            "type": "category",
            "id": "1",
            "name": "Автомобили",
            "subs": [
              {
                "type": "subcategory",
                "id": "1-1",
                "name": "Легковые автомобили",
                "fields": [
                  {
                    "type": "meta-tag",
                    "key": "",
                    "fields": [
                      {
                        "type": "photo-picker-field",
                        "key": "photos",
                        "count": 5
                      },
                      {
                        "type": "title-field",
                        "key": "Название",
                        "value": ""
                      },
                      {
                        "type": "price-field",
                        "key": "Стоимость",
                        "value": "",
                        "unitMeasure": "руб"
                      },
                      {
                        "type": "description-field",
                        "key": "Описание",
                        "value": ""
                      },
                      {
                        "type": "dropdown-field",
                        "key": "Эксплуатация",
                        "value": "С пробегом",
                        "options": ["С пробегом", "Без пробега"],
                        "isOnlyOneToChoose": true
                      },
                      {
                        "type": "meta-tag",
                        "key": "Основные параметры",
                        "fields": [
                          {
                            "type": "dropdown-field",
                            "key": "Марка",
                            "value": "Не указано",
                            "options": ["Porsche", "Chevrolet"],
                            "isOnlyOneToChoose": true
                          },
                          {
                            "type": "dropdown-field",
                            "key": "Модель",
                            "value": "Не указано",
                            "options": ["", ""],
                            "isOnlyOneToChoose": true
                          },
                          {
                            "type": "number-field",
                            "key": "Год выпуска",
                            "value": "",
                            "unitMeasure": "год"
                          },
                          {
                            "type": "dropdown-field",
                            "key": "Руль",
                            "value": "Не указано",
                            "options": ["", ""],
                            "isOnlyOneToChoose": true
                          },
                          {
                            "type": "dropdown-field",
                            "key": "Кузов",
                            "value": "Не указано",
                            "options": ["", ""],
                            "isOnlyOneToChoose": true
                          },
                          {
                            "type": "number-field",
                            "key": "Количество дверей",
                            "value": "",
                            "unitMeasure": "шт"
                          },
                          {
                            "type": "dropdown-field",
                            "key": "Цвет",
                            "value": "Не указано",
                            "options": ["", ""],
                            "isOnlyOneToChoose": true
                          }
                        ]
                      },
                      {
                        "type": "location-field",
                        "key": "location"
                      }
                    ]
                  }
                ]
              }
            ]
          },
          {
            "type": "category",
            "id": "2",
            "name": "Недвижимость",
            "subs": [
              {
                "type": "subcategory",
                "id": "2-1",
                "name": "Квартира",
                "fields": [
                  {
                    "type": "meta-tag",
                    "key": "",
                    "fields": [
                      {
                        "type": "photo-picker-field",
                        "key": "photos",
                        "count": 5
                      },
                      {
                        "type": "text-field",
                        "key": "Стоимость",
                        "value": ""
                      },
                      {
                        "type": "meta-tag",
                        "key": "Характеристики",
                        "fields": [
                          {
                            "type": "text-field",
                            "key": "make",
                            "value": ""
                          },
                          {
                            "type": "number-field",
                            "key": "year",
                            "value": "",
                            "unitMeasure": "год"
                          }
                        ]
                      },
                      {
                        "type": "dropdown-field",
                        "key": "fuel",
                        "value": "",
                        "options": ["Бензин", "Дизель", "Электро"],
                        "isOnlyOneToChoose": true
                      },
                      {
                        "type": "location-field",
                        "key": "location"
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ]  
    """

    suspend fun getCategories(): List<CategoryField> {

        val json = Json {
            classDiscriminator = "type"
            ignoreUnknownKeys = true // например, "type"
        }

        //val temp: List<CategoryDto> = categoryRepository.getAllCategories()

        //Log.d("Categories", temp.toString())

        val categoryFields = json.decodeFromString<List<CategoryField.Category>>(prompt)

        return categoryFields
    }

}