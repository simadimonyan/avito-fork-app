package samaryanin.avitofork.presentation.screens.start.state

sealed class AppEvent {
    data class FirstStartUp(val isFirstStartUp: Boolean) : AppEvent()
}