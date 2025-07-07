package samaryanin.avitofork.feature.poster.ui.state

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.feature.poster.domain.usecases.ConfigurationUseCase
import samaryanin.avitofork.feature.poster.domain.usecases.CreatePostUseCase
import samaryanin.avitofork.feature.poster.domain.usecases.UploadAdImageUseCase
import samaryanin.avitofork.shared.extensions.exceptions.safeScope
import javax.inject.Inject

@Stable
@HiltViewModel
class CategoryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val categoryStateHolder: CategoryStateHolder,
    private val configurationUseCase: ConfigurationUseCase,
    private val dataStore: DataStore<Preferences>,
    private val uploadAdImageUseCase: UploadAdImageUseCase,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val DRAFTS_KEY = stringPreferencesKey("drafts")

    init {
        safeScope.launch {
            Log.d("CategoryViewModel", "Init block started...")
            Log.d("CategoryViewModel", "Checking if drafts are empty...")
            if (categoryStateHolder.categoryState.value.drafts.isEmpty()) {
                Log.d("CategoryViewModel", "Drafts are empty. Starting to load from DataStore...")

                val jsonFormatter = Json {
                    ignoreUnknownKeys = true
                    classDiscriminator = "type"
                }

                withContext(Dispatchers.IO) {
                    Log.d("CategoryViewModel", "Reading drafts from DataStore...")
                    dataStore.data.collect { preferences ->
                        val json = preferences[DRAFTS_KEY]
                        Log.d("CategoryViewModel", "Raw JSON from DataStore: $json")
                        if (!json.isNullOrEmpty()) {
                            val state: Map<String, PostState> = try {
                                jsonFormatter.decodeFromString(json)
                            } catch (e: Exception) {
                                Log.e("CategoryViewModel", "Failed to parse JSON", e)
                                emptyMap()
                            }

                            categoryStateHolder.updateDrafts(state)
                            Log.d("CategoryViewModel", "Drafts updated in state holder.")
                        }
                    }
                }
            }
        }
    }

    fun handleEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.UpdateCategoryListConfiguration -> updateConfiguration()
            is CategoryEvent.SaveDraft -> saveDraft(event.subCategory)
            is CategoryEvent.UpdateDraftParams -> updateDraftParams(event.draft)
            //is CategoryEvent.PublishPost -> publish()
            is CategoryEvent.ClearDraft -> clearDraft(event.subCategory)
            // CategoryEvent.UploadPhoto should use uploadPhoto now, but usage may depend on call site
        }
    }

    private fun updateDraftParams(draft: PostState) {
        categoryStateHolder.updateTempDraftPost(draft)
    }

    suspend fun uploadPhoto(index: Int, uri: Uri): Boolean {
        return try {
            val id = uploadAdImageUseCase.upload(context, uri)
            if (id.isNotBlank()) {
                val state = categoryStateHolder.categoryState.value.tempDraft
                state.data.photos[index] = id
                categoryStateHolder.updateTempDraftPost(state)
                true
            } else {
                false
            }
        } catch (_: Exception) {
            false
        }
    }

    suspend fun publish(): Boolean {
        return try {
            val state = categoryStateHolder.categoryState.value.tempDraft
            createPostUseCase.create(state)
        }
        catch (_: Exception) {
            false
        }
    }

    private fun saveDraft(subCategory: String) {
        val state = categoryStateHolder.categoryState.value

        val draft = state.tempDraft
        val mapping = state.drafts.toMutableMap()

        mapping[subCategory] = draft

        categoryStateHolder.updateDrafts(mapping)
        saveDraftsStateToCache()
    }

    private fun clearDraft(subCategory: String) {
        val state = categoryStateHolder.categoryState.value

        val mapping = state.drafts.toMutableMap()

        mapping.remove(subCategory)

        categoryStateHolder.updateDrafts(mapping)
        saveDraftsStateToCache()
    }

    private fun updateConfiguration() {
        safeScope.launch {
            categoryStateHolder.updateLoading(true)
            categoryStateHolder.updateCategories(configurationUseCase.getCategories())
            categoryStateHolder.updateLoading(false)
        }
    }

    // ---

    private fun saveDraftsStateToCache() {
        safeScope.launch {
            Log.d("CategoryViewModel", "Saving drafts to DataStore...")

            val json = Json.encodeToString(categoryStateHolder.categoryState.value.drafts)
            Log.d("CategoryViewModel", "Serialized JSON to save: $json")

            withContext(Dispatchers.IO) {
                dataStore.edit { preferences ->
                    preferences[DRAFTS_KEY] = json
                }
                Log.d("CategoryViewModel", "Drafts successfully saved to DataStore.")
            }
        }
    }

}