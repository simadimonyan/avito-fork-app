package samaryanin.avitofork.feature.poster.ui.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.poster.domain.models.PostState

sealed class CategoryEvent {

    /**
     * Обновить конфигурацию категорий объявлений с сервера
     */
    object UpdateCategoryListConfiguration : CategoryEvent()

    /**
     * Добавить пост в черновики
     */
    @Immutable
    data class SaveDraft(val subCategory: String) : CategoryEvent()

    /**
     * Удалить пост из черновиков
     */
    @Immutable
    data class ClearDraft(val subCategory: String) : CategoryEvent()

//    /**
//     * Опубликовать объявление
//     */
//    object PublishPost : CategoryEvent()

    /**
     * Обновить данные черновика
     */
    @Immutable
    data class UpdateDraftParams(val draft: PostState) : CategoryEvent()

//    /**
//     * Загрузить фото на сервер
//     */
//    @Immutable
//    data class UploadPhoto(val place: Int, val uri: Uri) : CategoryEvent()

}