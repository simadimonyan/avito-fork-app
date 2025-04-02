package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.domain.model.post.PostState

sealed class CategoryEvent {

    /**
     * Обновить конфигурацию категорий объявлений с сервера
     */
    object UpdateCategoryListConfiguration : CategoryEvent()

    /**
     * Добавить пост в черновики
     */
    object SaveDraftToCache : CategoryEvent()

    /**
     * Опубликовать объявление
     */
    object PublishPost : CategoryEvent()

    /**
     * Обновить данные черновика
     */
    @Immutable
    data class UpdateDraftParams(val draft: PostState) : CategoryEvent()

}