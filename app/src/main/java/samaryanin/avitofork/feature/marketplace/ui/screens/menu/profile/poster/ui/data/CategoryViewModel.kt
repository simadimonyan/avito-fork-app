package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.ui.data

import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import samaryanin.avitofork.core.domain.PostUseCase
import samaryanin.avitofork.core.ui.start.data.state.AppStateStore
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import javax.inject.Inject

@Stable
@HiltViewModel
class CategoryViewModel @Inject constructor(
    val appStateStore: AppStateStore,
    private val postUseCase: PostUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val DRAFTS_KEY = stringSetPreferencesKey("drafts")

    fun handleEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.UpdateCategoryListConfiguration -> updateConfiguration()
            is CategoryEvent.SaveDraft -> saveDraft(event.subCategory)
            is CategoryEvent.UpdateDraftParams -> updateDraftParams(event.draft)
            is CategoryEvent.PublishPost -> publish()
            is CategoryEvent.ClearDraft -> clearDraft(event.subCategory)
        }
    }

    private fun updateDraftParams(draft: PostState) {
        appStateStore.categoryStateHolder.updateTempDraftPost(draft)
    }

    private fun publish() {
        // TODO (опубликовать объявление)
    }

    private fun saveDraft(subCategory: String) {
        val state = appStateStore.categoryStateHolder.categoryState.value

        val draft = state.tempDraft
        val mapping = state.drafts.toMutableMap()

        mapping[subCategory] = draft

        appStateStore.categoryStateHolder.updateDrafts(mapping)
        saveDraftsStateToCache()
    }

    private fun clearDraft(subCategory: String) {
        val state = appStateStore.categoryStateHolder.categoryState.value

        val mapping = state.drafts.toMutableMap()

        mapping.remove(subCategory)

        appStateStore.categoryStateHolder.updateDrafts(mapping)
        saveDraftsStateToCache()
    }

    private fun updateConfiguration() {

        viewModelScope.launch {
            appStateStore.categoryStateHolder.updateLoading(true)
            appStateStore.categoryStateHolder.updateCategories(postUseCase.configurationUseCase.getCategories())
            appStateStore.categoryStateHolder.updateLoading(false)
        }

    }

    // ---

    private fun saveDraftsStateToCache() {
        viewModelScope.launch {

            val gson = Gson()
            val json = gson.toJson(appStateStore.categoryStateHolder.categoryState.value.drafts)

            withContext(Dispatchers.IO) {
                dataStore.edit { preferences ->
                    preferences[DRAFTS_KEY] = mutableSetOf(json)
                }
            }
        }
    }

}