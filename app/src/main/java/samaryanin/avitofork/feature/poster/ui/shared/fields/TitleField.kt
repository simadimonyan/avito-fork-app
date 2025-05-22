package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.runtime.Composable
import samaryanin.avitofork.feature.poster.domain.models.PostData

@Composable
fun TitleField(updateDraft: (PostData) -> Unit, data: PostData, key: String) {
    TextField(key = key, value = data.name, placeholder = "") {
        updateDraft(data.copy(name = it))
    }
}