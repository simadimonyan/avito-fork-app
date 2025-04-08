package samaryanin.avitofork.domain.repository

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
data class DatabaseRepository @Inject constructor(

    val temp: String

)