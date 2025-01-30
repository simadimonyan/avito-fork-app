package samaryanin.avitofork.presentation.screens.start.state

sealed class AppEvent {

    /**
     * Устанавливает состояние первого запуска приложения
     */
    data class FirstStartUp(val isFirstStartUp: Boolean) : AppEvent()

    /**
     * Сохраняет главное состояние приложения в кеш
     */
    object SaveAppState : AppEvent()

    /**
     * Возвращает сохраненное состояние из кеша
     */
    object RestoreCache : AppEvent()


    //-----------Auth-------------

    object ToggleAuthRequest : AppEvent()

}