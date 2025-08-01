package samaryanin.avitofork.feature.profile.ui.state.profile

import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import samaryanin.avitofork.app.activity.data.AppStateHolder
import samaryanin.avitofork.feature.auth.ui.state.AuthStateHolder
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.feature.poster.ui.state.CategoryStateHolder
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor(
    val appStateHolder: AppStateHolder,
    val authStateHolder: AuthStateHolder,
    val profileStateHolder: ProfileStateHolder,
    val categoryStateHolder: CategoryStateHolder,
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    private val DRAFTS_KEY = stringSetPreferencesKey("drafts")

    init { // загрузка черновиков из кеша
        safeScope.launch {
            if (categoryStateHolder.categoryState.value.drafts.isEmpty()) {

                val gson = Gson()

                withContext(Dispatchers.IO) {
                    dataStore.data.collect { preferences ->
                        val jsonSet = preferences[DRAFTS_KEY] ?: emptySet()

                        if (jsonSet.isNotEmpty()) {
                            val type = object : TypeToken<Map<String, PostState>>() {}.type

                            val state: Map<String, PostState> = try {
                                gson.fromJson(jsonSet.first(), type)
                            } catch (e: Exception) {
                                mutableMapOf()
                            }

                            categoryStateHolder.updateDrafts(state)
                        }
                    }
                }
            }
        }
    }

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadPublications -> loadPublications()
            is ProfileEvent.AddPublication -> addPublication(event.post)
        }
    }

    private fun addPublication(post: PostState) {
        val currentPosts = profileStateHolder.profileState.value.posts
        val map = HashMap(currentPosts)
        val list = ArrayList(map["0"] ?: emptyList())

        list.add(post)
        map["0"] = list

        profileStateHolder.updatePostsList(map)

    }

    private fun loadPublications() {
        // TODO (загрузка публикаций с сервера)
    }

}