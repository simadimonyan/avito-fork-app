package samaryanin.avitofork.shared.extensions

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T> MutableStateFlow<T>.emitIfChanged(newValue: T) {
    if (value != newValue) value = newValue
}