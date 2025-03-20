package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import samaryanin.avitofork.domain.model.post.PostState
import samaryanin.avitofork.domain.usecase.PostUseCase
import samaryanin.avitofork.presentation.state.AppStateStore
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val postUseCase: PostUseCase
) : ViewModel() {

    fun handleEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.UpdateCategoryListConfiguration -> updateConfiguration()
            is CategoryEvent.SaveDraftToCache -> saveDraftToCache()
            is CategoryEvent.UpdateDraftParams -> updateDraftParams(event.draft)
            is CategoryEvent.PublishPost -> publish()
        }
    }

    private fun updateDraftParams(draft: PostState) {
        appStateStore.categoryStateHolder.updateTempDraftPost(draft)
    }

    private fun publish() {
        // TODO (опубликовать объявление)
    }

    private fun saveDraftToCache() {
        //TODO (сохранить черновик в кеш)
    }

    private fun updateConfiguration() {

        viewModelScope.launch {
            appStateStore.categoryStateHolder.updateLoading(true)
            appStateStore.categoryStateHolder.updateCategories(postUseCase.configurationUseCase.getCategories())
            appStateStore.categoryStateHolder.updateLoading(false)
        }

    }

}