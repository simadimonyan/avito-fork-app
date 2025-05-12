package samaryanin.avitofork.feature.poster.ui.state

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.feature.poster.domain.usecases.ConfigurationUseCase
import samaryanin.avitofork.feature.poster.domain.usecases.CreatePostUseCase
import samaryanin.avitofork.feature.poster.domain.usecases.UploadAdImageUseCase
import samaryanin.avitofork.shared.state.AppStateStore
import samaryanin.avitofork.shared.view_model.safeScope
import javax.inject.Inject

@Stable
@HiltViewModel
class CategoryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val appStateStore: AppStateStore,
    private val configurationUseCase: ConfigurationUseCase,
    private val dataStore: DataStore<Preferences>,
    private val uploadAdImageUseCase: UploadAdImageUseCase,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val DRAFTS_KEY = stringSetPreferencesKey("drafts")

    fun handleEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.UpdateCategoryListConfiguration -> updateConfiguration()
            is CategoryEvent.SaveDraft -> saveDraft(event.subCategory)
            is CategoryEvent.UpdateDraftParams -> updateDraftParams(event.draft)
            is CategoryEvent.PublishPost -> publish()
            is CategoryEvent.ClearDraft -> clearDraft(event.subCategory)
            is CategoryEvent.UploadPhoto -> uploadImage(event.place, event.uri)
        }
    }

    private fun updateDraftParams(draft: PostState) {
        appStateStore.categoryStateHolder.updateTempDraftPost(draft)
    }

    private fun uploadImage(place: Int, uri: Uri) {
        safeScope.launch {
            val id = uploadAdImageUseCase.upload(context, uri)
            val state = appStateStore.categoryStateHolder.categoryState.value.tempDraft
            state.data.photos.put(place, id)
            appStateStore.categoryStateHolder.updateTempDraftPost(state)
        }
    }

    private fun publish() {
        viewModelScope.launch {
            val state = appStateStore.categoryStateHolder.categoryState.value.tempDraft
            createPostUseCase.create(state)
        }
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

        safeScope.launch {
            appStateStore.categoryStateHolder.updateLoading(true)
            appStateStore.categoryStateHolder.updateCategories(configurationUseCase.getCategories())
            appStateStore.categoryStateHolder.updateLoading(false)
        }

    }

    // ---

    private fun saveDraftsStateToCache() {
        safeScope.launch {

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