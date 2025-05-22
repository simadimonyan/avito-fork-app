package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.runtime.Composable
import samaryanin.avitofork.feature.poster.domain.models.PostData

@Composable
fun DescriptionField(updateDraft: (PostData) -> Unit, data: PostData, key: String) {
    TextField(key = key, value = data.description, placeholder = "До 3000 символов") {
        updateDraft(data.copy(description = it))
    }
}