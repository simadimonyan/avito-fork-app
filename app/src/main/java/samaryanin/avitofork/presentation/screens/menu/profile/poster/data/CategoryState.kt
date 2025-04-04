package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import samaryanin.avitofork.domain.model.post.CategoryField
import samaryanin.avitofork.domain.model.post.PostState
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
data class CategoryState(

    /**
     * Список категорий объявлений (включает в себя подкатегории и их поля)
     */
    val categories: List<CategoryField> = mutableStateListOf(),

    /**
     * Список черновиков по категориям и подкатегориям
     */
    val drafts: Map<String, PostState> = mutableMapOf(),

    /**
     * Временный черновик для сборки поста в список черновиков или публикации
     */
    val tempDraft: PostState = PostState(),

    /**
     * Состояние подгрузки категорий
     */
    val isLoading: Boolean = false

)

@Singleton
@Immutable
class CategoryStateHolder @Inject constructor() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState

    fun updateCategories(categories: List<CategoryField>) {
        _categoryState.update { it.copy(categories = categories) }
    }

    fun updateLoading(isLoading: Boolean) {
        _categoryState.update { it.copy(isLoading = isLoading) }
    }

    fun updateDrafts(drafts: Map<String, PostState>) {
        _categoryState.update { it.copy(drafts = drafts) }
    }

    fun updateTempDraftPost(temp: PostState) {
        _categoryState.update { it.copy(tempDraft = temp) }
    }

}