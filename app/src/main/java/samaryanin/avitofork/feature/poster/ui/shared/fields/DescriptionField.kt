package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.runtime.Composable
import samaryanin.avitofork.feature.poster.domain.models.PostData

@Composable
fun DescriptionField(
    updateDraft: (PostData) -> Unit,
    data: PostData,
    key: String,
    isRequired: Boolean,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit
) {
    TextField(
        key = key, value = data.description, placeholder = "До 3000 символов",
        isRequired = isRequired,
        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
        showErrorMessage = showErrorMessage
    ) {
        updateDraft(data.copy(description = it))
    }
}