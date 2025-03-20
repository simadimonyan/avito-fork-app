package samaryanin.avitofork.presentation.screens.menu.profile.poster.data

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
    data class UpdateDraftParams(val draft: PostState) : CategoryEvent()

}