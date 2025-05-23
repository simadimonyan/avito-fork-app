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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.shared.ui.components.utils.space.Divider
import samaryanin.avitofork.shared.ui.theme.veryLightGray
import kotlin.collections.forEach
import kotlin.collections.set

@Preview
@Composable
fun FieldsPreview() {

    val gap: (Int, Uri) -> Unit = { i, a -> }

    Column {
        MetaTag(
            key = "",
            fields = mutableListOf(
                CategoryField.PhotoPickerField("", 8),
                CategoryField.TextField("Описание:", ""),
                CategoryField.TextField("Описание 1:", ""),
            ),
            data = PostData(),
            observer = {},
            uploadPhoto = gap
        )
        MetaTag(
            key = "Характеристики 2",
            fields = mutableListOf(
                CategoryField.NumberField("Год выпуска:", "", "г"),
                CategoryField.NumberField("Объем двигателя:", "", "л")
            ),
            data = PostData(),
            observer = {},
            uploadPhoto = gap
        )
        MetaTag(
            key = "Характеристики 3",
            fields = mutableListOf(
                CategoryField.DropdownField(
                    "Тип недвижимости:",
                    "Не выбран",
                    mutableListOf(),
                    true
                ),
                CategoryField.LocationField("Местоположение строения")
            ),
            data = PostData(),
            observer = {},
            uploadPhoto = gap
        )
    }
}

/**
 * Мета-разделитель и корневой элемент, содержащий абстрактные блоки для создания объявления
 *
 * @param key имя разделителя характеристик
 *
 * @param fields поля для карточки товара
 * @param data состояние данных по карточке товара
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
    params: SnapshotStateMap<String, String> = remember { mutableStateMapOf() },
    data: PostData,

    // колбек функции для работы с данными
    observer: (PostData) -> Unit,
    uploadPhoto: (Int, Uri) -> Unit,
    isRequiredCheckSubmitted: Boolean = false,
    showErrorMessage: (String) -> Unit = {}
) {

    LaunchedEffect(isRequiredCheckSubmitted) {
        Log.d("MetaTag", "validateFields triggered, isRequiredCheckSubmitted = $isRequiredCheckSubmitted")
    }

    Box(modifier = Modifier.background(veryLightGray)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (key.isNotEmpty()) Text(modifier = Modifier.padding(10.dp), text = key, fontSize = 15.sp, color = Color.Gray, fontWeight = FontWeight.Medium)

            fields.forEach { field ->

                val index = fields.indexOf(field)
                if (index != 0) {
                    Divider()
                }

                when (field) {

                    // data передается и для обновления колбеком и для получения данных из черновиков в value
                    is CategoryField.PriceField -> PriceField(
                        observer, data, field.key, field.unitMeasure, field.isRequired,
                        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                        showErrorMessage = showErrorMessage
                    )
                    is CategoryField.DescriptionField -> DescriptionField(observer, data, field.key)
                    is CategoryField.TitleField -> TitleField(observer, data, field.key)

                    is CategoryField.TextField -> {
                        // data.options[field.key] - поиск значения поля по ключу поля
                        TextField(field.key, data.options[field.key] ?: field.value, "до 3000 символов") {
                            params[field.key] = it
                            observer(data.copy(options = params))
                        }
                    }

                    is CategoryField.DropdownField -> DropdownField(observer, field.key, field.value, field.options, field.isOnlyOneToChoose)
                    is CategoryField.LocationField -> LocationField(observer, field.key)

                    is CategoryField.NumberField -> {
                        // data.options[field.key] - поиск значения поля по ключу поля
                        NumberField(
                            field.key,
                            data.options[field.key] ?: field.value,
                            field.unitMeasure,
                            field.value,
                            visualTransformation = VisualTransformation.None,
                            isRequired = field.isRequired,
                            isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                            showErrorMessage = showErrorMessage,
                        ) {
                            params[field.key] = it
                            observer(data.copy(options = params))
                        }
                    }

                    is CategoryField.PhotoPickerByCategoryField -> TODO()
                    is CategoryField.PhotoPickerField -> PhotoPickerField(observer, field.key, field.count, uploadPhoto)

                    // вложенный разделитель внутри корневого
                    is CategoryField.MetaTag -> MetaTag(
                        field.key,
                        field.fields,
                        params,
                        data,
                        observer,
                        uploadPhoto,
                        isRequiredCheckSubmitted,
                        showErrorMessage
                    )
                    else -> {}

                }

            }

        }
    }
}