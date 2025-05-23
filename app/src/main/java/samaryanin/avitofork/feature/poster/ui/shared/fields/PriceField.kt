package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.runtime.Composable
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.ui.shared.text.PriceVisualTransformation

@Composable
fun PriceField(
    updateDraft: (PostData) -> Unit,
    data: PostData,
    key: String,
    unitMeasure: String,
    isRequired: Boolean,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit
) {
    NumberField(
        key = key,
        value = data.price,
        unitMeasure = unitMeasure,
        visualTransformation = PriceVisualTransformation(),
        placeholder = "",
        isRequired = isRequired,
        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
        showErrorMessage = showErrorMessage
    ) {
        updateDraft(data.copy(price = it, unit = unitMeasure))
    }
}
