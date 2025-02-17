package samaryanin.avitofork.presentation.screens.start.data

import androidx.compose.runtime.Immutable

@Immutable
data class AppState(

    /**
    * Глобальные настройки приложения
    */
    val isFirstStartUp: Boolean = true,
    val isLoggedIn: Boolean = false,


    /**
     * Событийные состояния
     */
    val authRequested: Boolean = false

)