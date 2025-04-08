package samaryanin.avitofork.core.ui.start.data.state

import androidx.compose.runtime.Immutable

sealed class AppEvent {

    /**
     * Устанавливает состояние первого запуска приложения
     */
    @Immutable
    data class FirstStartUp(val isFirstStartUp: Boolean) : AppEvent()

    /**
     * Сохраняет главное состояние приложения в кеш
     */
    object SaveAppState : AppEvent()

    /**
     * Возвращает сохраненное состояние из кеша
     */
    object RestoreCache : AppEvent()



    //-----------AUTH-------------

    /**
     * Пользователь авторизовался в профиль
     */
    object ProfileHasLoggedIn : AppEvent()

    /**
     * Вызвать окно для авторизации
     */
    object ToggleAuthRequest : AppEvent()

}