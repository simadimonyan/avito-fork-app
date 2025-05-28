package samaryanin.avitofork.feature.poster.ui.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.domain.models.PostState
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
@Serializable
data class CategoryState(

    /**
     * Список категорий объявлений (включает в себя подкатегории и их поля)
     */
    @Serializable val categories: List<CategoryField> = mutableStateListOf(),

    /**
     * Список черновиков по категориям и подкатегориям
     */
    @Serializable val drafts: Map<String, PostState> = mutableMapOf(),

    /**
     * Временный черновик для сборки поста в список черновиков или публикации
     */
    @Serializable val tempDraft: PostState = PostState(),

    /**
     * Состояние подгрузки категорий
     */
    @Serializable val isLoading: Boolean = false,

    /**
     * Состояние для диалогового окна вывода ошибки обязательного поля
     */
    @Serializable val lastErrorField: String = ""

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

    fun updateLastErrorField(error: String) {
        _categoryState.update { it.copy(lastErrorField = error) }
    }

}