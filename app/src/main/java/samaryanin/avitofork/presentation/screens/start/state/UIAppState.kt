package samaryanin.avitofork.presentation.screens.start.state

import androidx.compose.runtime.Immutable

@Immutable
data class UIAppState(

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