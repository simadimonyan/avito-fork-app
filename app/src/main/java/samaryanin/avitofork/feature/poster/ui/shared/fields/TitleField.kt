package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.runtime.Composable
import samaryanin.avitofork.feature.poster.domain.models.PostData

@Composable
fun TitleField(
    updateDraft: (PostData) -> Unit,
    data: PostData,
    key: String,
    isRequired: Boolean,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit
) {
    TextField(
        key = key, value = data.name, placeholder = "",
        isRequired = isRequired,
        isRequiredCheckSubmitted = isRequiredCheckSubmitted,
        showErrorMessage = showErrorMessage
    ) {
        updateDraft(data.copy(name = it))
    }
}