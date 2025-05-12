package samaryanin.avitofork.shared.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus

private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("ERROR", throwable.message.toString())
}

val ViewModel.safeScope: CoroutineScope
    get() = viewModelScope + coroutineExceptionHandler