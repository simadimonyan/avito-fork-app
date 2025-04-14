package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.data

import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    private val DRAFTS_KEY = stringSetPreferencesKey("drafts")

    init { // загрузка черновиков из кеша
        viewModelScope.launch {
            if (appStateStore.categoryStateHolder.categoryState.value.drafts.isEmpty()) {

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

                            appStateStore.categoryStateHolder.updateDrafts(state)
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
        val currentPosts = appStateStore.profileStateHolder.profileState.value.posts
        val map = HashMap(currentPosts)
        val list = ArrayList(map["0"] ?: emptyList())

        list.add(post)
        map["0"] = list

        appStateStore.profileStateHolder.updatePostsList(map)

    }

    private fun loadPublications() {
        // TODO (загрузка публикаций с сервера)
    }

}