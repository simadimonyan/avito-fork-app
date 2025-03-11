package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
            CategoryEvent.UpdateCategoryListConfiguration -> updateConfiguration()
        }
    }

    private fun updateConfiguration() {

        viewModelScope.launch {
            appStateStore.categoryStateHolder.updateLoading(true)
            appStateStore.categoryStateHolder.updateCategories(postUseCase.configurationUseCase.getCategories())
            appStateStore.categoryStateHolder.updateLoading(false)
        }

    }

}