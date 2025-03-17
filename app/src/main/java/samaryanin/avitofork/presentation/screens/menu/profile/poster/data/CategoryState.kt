package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import samaryanin.avitofork.domain.model.post.CategoryField
import javax.inject.Inject
import javax.inject.Singleton

@Stable
data class CategoryState(

    /**
     * Список категорий объявлений (включает в себя подкатегории и их поля)
     */
    val categories: List<CategoryField> = mutableStateListOf(),

    /**
     * Список черновиков подкатегорий объявлений (включает в себя их поля)
     */
    val drafts: List<CategoryField> = mutableListOf(),

    /**
     * Состояние подгрузки категорий
     */
    val isLoading: Boolean = false

)

@Singleton
class CategoryStateHolder @Inject constructor() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState

    fun updateCategories(categories: List<CategoryField>) {
        _categoryState.update { it.copy(categories = categories) }
    }

    fun updateLoading(isLoading: Boolean) {
        _categoryState.update { it.copy(isLoading = isLoading) }
    }

}