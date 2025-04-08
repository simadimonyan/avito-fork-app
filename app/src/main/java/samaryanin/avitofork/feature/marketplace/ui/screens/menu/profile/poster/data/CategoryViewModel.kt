package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.auth.domain.usecase.PostUseCase
import javax.inject.Inject

@Stable
@HiltViewModel
class CategoryViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val postUseCase: PostUseCase
) : ViewModel() {

    fun handleEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.UpdateCategoryListConfiguration -> updateConfiguration()
            is CategoryEvent.SaveDraft -> saveDraft(event.subCategory)
            is CategoryEvent.UpdateDraftParams -> updateDraftParams(event.draft)
            is CategoryEvent.PublishPost -> publish()
            is CategoryEvent.ClearDraft -> clearDraft(event.subCategory)
        }
    }

    private fun updateDraftParams(draft: PostState) {
        appStateStore.categoryStateHolder.updateTempDraftPost(draft)
    }

    private fun publish() {
        // TODO (опубликовать объявление)
    }

    private fun saveDraft(subCategory: String) {
        val state = appStateStore.categoryStateHolder.categoryState.value

        val draft = state.tempDraft
        val mapping = state.drafts.toMutableMap()

        mapping[subCategory] = draft

        appStateStore.categoryStateHolder.updateDrafts(mapping)
    }

    private fun clearDraft(subCategory: String) {
        val state = appStateStore.categoryStateHolder.categoryState.value

        val mapping = state.drafts.toMutableMap()

        mapping.remove(subCategory)

        appStateStore.categoryStateHolder.updateDrafts(mapping)
    }

    private fun updateConfiguration() {

        viewModelScope.launch {
            appStateStore.categoryStateHolder.updateLoading(true)
            appStateStore.categoryStateHolder.updateCategories(postUseCase.configurationUseCase.getCategories())
            appStateStore.categoryStateHolder.updateLoading(false)
        }

    }

}