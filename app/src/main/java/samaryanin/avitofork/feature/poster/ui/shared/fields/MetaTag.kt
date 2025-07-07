package samaryanin.avitofork.feature.poster.ui.shared.fields

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.shared.ui.components.utils.space.Divider
import samaryanin.avitofork.shared.ui.theme.veryLightGray

@Preview
@Composable
fun FieldsPreview() {

    val gap: (Int, Uri, (Boolean) -> Unit) -> Unit = { index, uri, callback ->}

    Column {
        MetaTag(
            key = "",
            fields = mutableListOf(
                CategoryField.PhotoPickerField("", "", "", 8),
                CategoryField.TextField("", "", "Описание:", ""),
                CategoryField.TextField("", "", "Описание 1:", ""),
            ),
            mutableMapOf(),
            draft = PostData(),
            observer = {},
            uploadPhoto = gap,
            isRequiredCheckSubmitted = false,
        )
        MetaTag(
            key = "Характеристики 2",
            fields = mutableListOf(
                CategoryField.NumberField("", "", "Год выпуска:", "", "г"),
                CategoryField.NumberField("", "", "Объем двигателя:", "", "л")
            ),
            mutableMapOf(),
            draft = PostData(),
            observer = {},
            uploadPhoto = gap,
            isRequiredCheckSubmitted = false,
        )
        MetaTag(
            key = "Характеристики 3",
            fields = mutableListOf(
                CategoryField.DropdownField(
                    "",
                    "",
                    "Тип недвижимости:",
                    "Не выбран",
                    mutableListOf(),
                    true
                ),
                CategoryField.LocationField("", "", "Местоположение строения")
            ),
            mutableMapOf(),
            draft = PostData(),
            observer = {},
            uploadPhoto = gap,
            isRequiredCheckSubmitted = false,
        )
    }
}

/**
 * Мета-разделитель и корневой элемент, содержащий абстрактные блоки для создания объявления
 *
 * @param key имя разделителя характеристик
 *
 * @param fields поля для карточки товара
 * @param draft состояние данных по карточке товара
 *
 * @param observer колбек функция для обновления данных с блоков полей
 * @param uploadPhoto колбек функция для загрузки фотографий
 */
@Composable
fun MetaTag(
    // разделитель
    key: String,

    // данные по карточке товара
    fields: List<CategoryField>,
    params: MutableMap<String, CategoryField>,
    draft: PostData,

    // колбек функции для работы с данными
    observer: (PostData) -> Unit,
    uploadPhoto: (Int, Uri, (Boolean) -> Unit) -> Unit,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit = {},
    determineLocation: () -> Unit = {}
) {

    Box(modifier = Modifier.background(veryLightGray)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (key.isNotEmpty()) Text(modifier = Modifier.padding(10.dp), text = key, fontSize = 15.sp, color = Color.Gray, fontWeight = FontWeight.Medium)

            fields.forEach { field ->

                val index = fields.indexOf(field)
                if (index != 0) {
                    Divider()
                }

                when (field) {

                    // draft передается и для обновления колбеком и для получения данных из черновиков в value
                    is CategoryField.PriceField -> PriceField(
                        observer, draft, field.key, field.unitMeasure, field.isRequired,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        showErrorMessage = showErrorMessage
                    )
                    is CategoryField.DescriptionField -> DescriptionField(
                        observer, draft, field.key,
                        isRequired = field.isRequired,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        showErrorMessage = showErrorMessage
                    )
                    is CategoryField.TitleField -> TitleField(
                        observer, draft, field.key,
                        isRequired = field.isRequired,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        showErrorMessage = showErrorMessage
                    )

                    is CategoryField.TextField -> {
                        // draft.options[field.key] - поиск значения поля по ключу поля
                        TextField(
                            field.key, (draft.options[field.key] as? CategoryField.TextField)?.value ?: field.value, "до 3000 символов",
                            isRequired = field.isRequired,
                            isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                            showErrorMessage = showErrorMessage
                        ) {
                            val updatedField = field.copy(value = it)
                            params[field.key] = updatedField
                            observer(draft.copy(options = params))
                        }
                    }

                    is CategoryField.DropdownField -> DropdownField(
                        // draft.options[field.key] - поиск значения поля по ключу поля
                        field.key, (draft.options[field.key] as? CategoryField.DropdownField)?.value ?: field.value, field.options, field.isOnlyOneToChoose,
                        isRequired = field.isRequired,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        showErrorMessage = showErrorMessage
                    ) {
                        val updatedField = field.copy(value = it)
                        params[field.key] = updatedField
                        observer(draft.copy(options = params))
                    }

                    is CategoryField.LocationField -> LocationField(
                        field.key, draft.location.fullText, determineLocation,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        isRequired = field.isRequired,
                        showErrorMessage = showErrorMessage
                    )

                    is CategoryField.NumberField -> {
                        // data.options[field.key] - поиск значения поля по ключу поля
                        NumberField(
                            field.key,
                            (draft.options[field.key] as? CategoryField.NumberField)?.value ?: field.value,
                            field.unitMeasure,
                            field.value,
                            visualTransformation = VisualTransformation.None,
                            isRequired = field.isRequired,
                            isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                            showErrorMessage = showErrorMessage,
                        ) {
                            val updatedField = field.copy(value = it)
                            params[field.key] = updatedField
                            observer(draft.copy(options = params))
                        }
                    }

                    is CategoryField.PhotoPickerByCategoryField -> TODO()

                    is CategoryField.PhotoPickerField -> PhotoPickerField(
                        observer, field.key, draft.photos, field.count, uploadPhoto,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        showErrorMessage = showErrorMessage
                    )

                    // вложенный разделитель внутри корневого
                    is CategoryField.MetaTag -> MetaTag(
                        field.key,
                        field.fields,
                        params,
                        draft,
                        observer,
                        uploadPhoto,
                        isRequiredCheckSubmitted,
                        showErrorMessage,
                        determineLocation
                    )
                    else -> {}

                }

            }

        }
    }
}