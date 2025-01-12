package samaryanin.avitofork.presentation.screens.start.state

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import samaryanin.avitofork.data.cache.CacheManager
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val cacheManager: CacheManager
) : ViewModel() {



}