package samaryanin.avitofork.feature.poster.ui.feature.poster

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.BackEventCompat
import androidx.activity.compose.PredictiveBackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import samaryanin.avitofork.R
import samaryanin.avitofork.app.navigation.MainRoutes
import samaryanin.avitofork.feature.poster.domain.models.CategoryField
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.feature.poster.ui.navigation.PostRoutes
import samaryanin.avitofork.feature.poster.ui.shared.fields.MetaTag
import samaryanin.avitofork.feature.poster.ui.state.CategoryEvent
import samaryanin.avitofork.feature.poster.ui.state.CategoryViewModel
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileEvent
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel
import samaryanin.avitofork.shared.ui.components.utils.space.Divider
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.components.utils.text.AppTextTitle
import kotlin.coroutines.cancellation.CancellationException

@Preview
@Composable
private fun PostCreatePreview() {

    val sample = CategoryField.SubCategory(
        "", "Тестовая подкатегория", "", "", "",
        mutableListOf(
            CategoryField.MetaTag(
                key = "",
                fields = mutableListOf(
                    CategoryField.PhotoPickerField("", "", "", 8),
                    CategoryField.TextField("", "", "Описание:", ""),
                    CategoryField.TextField("", "","Описание 1:", ""),
                )
            ),
            CategoryField.MetaTag(
                key = "Характеристики 2",
                fields = mutableListOf(
                    CategoryField.NumberField("", "", "Год выпуска:", "", "г"),
                    CategoryField.NumberField("", "", "Объем двигателя:", "", "л")
                )
            ),
            CategoryField.MetaTag(
                key = "Характеристики 3",
                fields = mutableListOf(
                    CategoryField.DropdownField(
                        "",
                        "",
                        "Тип недвижимости:",
                        "Не выбран",
                        mutableListOf(),
                        true
                    ),
                    CategoryField.LocationField("", "", "Местоположение строения")
                )
            )
        )
    )

    val gap: (Int, Uri, (Boolean) -> Unit) -> Unit = { index, uri, callback ->}
    val gap2 = {}

    PostCreateContent(
        onExit = { true },
        subcategory = sample,
        updateDraft = {},
        onPublish = {},
        draft = PostData(),
        uploadPhoto = gap,
        isRequiredCheckSubmitted = false,
        showErrorMessage = {},
        lastErrorField = "",
        showRenderBlocksErrorDialog = false,
        showPublishErrorDialog = false,
        dismissRenderBlocksErrorDialog = {},
        showPublishProgressDialog = false,
        determineLocation = gap2,
        dismissPublishErrorDialog = gap2
    )
}

@Composable
fun PostCreateScreen(
    globalNavController: NavController,
    subcategory: CategoryField.SubCategory,
    categoriesViewModel: CategoryViewModel,
    profileViewModel: ProfileViewModel,
) {
    val context = LocalContext.current
    val draftPost by categoriesViewModel.categoryStateHolder.categoryState.collectAsState()

    val onExit = {
        val priceField = draftPost.tempDraft.data.price
        val descriptionField = draftPost.tempDraft.data.description
        val optionsField = draftPost.tempDraft.data.options.size

        if (priceField != "" || descriptionField != "" || optionsField != 0) {
            categoriesViewModel.handleEvent(CategoryEvent.SaveDraft(subcategory.name))
            Toast.makeText(context, "Черновик объявления сохранен", Toast.LENGTH_LONG).show()
        }

        globalNavController.navigateUp()
    }

    val lastErrorField = draftPost.lastErrorField
    val showRenderBlocksErrorDialog = remember { mutableStateOf(false) }
    val showPublishErrorDialog = remember { mutableStateOf(false) }
    val showPublishProgressDialog = remember { mutableStateOf(false) }

    // --- LaunchedEffect validation state ---
    var isPublishTriggered by remember { mutableStateOf(false) }

    val onPublish: () -> Unit = {
        isPublishTriggered = true
    }

    val updateDraft: (PostData) -> Unit = { data ->
        categoriesViewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(draftPost.tempDraft.categoryName, subcategory.id, data)))
    }

    val scope = rememberCoroutineScope()

    val uploadPhoto: (Int, Uri, (Boolean) -> Unit) -> Unit = { index, uri, callback ->
        scope.launch {
            val success = categoriesViewModel.uploadPhoto(index, uri)
            callback(success)
        }
    }

    // перейти на экран для определения местоположения
    val determineLocation: () -> Unit = {
        globalNavController.navigate(PostRoutes.LocationScreen) {
            launchSingleTop = true
            restoreState = true
        }
    }

    val draft = draftPost.tempDraft.data

    if (subcategory.children.isEmpty()) { // если нет вложенных подкатегорий у подкатегории
        PostCreateContent(
            onExit = onExit,
            subcategory = subcategory,
            updateDraft = updateDraft,
            onPublish = onPublish,
            draft = draft,
            uploadPhoto = uploadPhoto,
            isRequiredCheckSubmitted = isPublishTriggered,
            showErrorMessage = {
                Log.d("Validation", "Field with error: $it")
                categoriesViewModel.categoryStateHolder.updateLastErrorField(it)
            },
            lastErrorField = lastErrorField,
            showRenderBlocksErrorDialog = showRenderBlocksErrorDialog.value,
            dismissPublishErrorDialog = { showPublishErrorDialog.value = false },
            showPublishErrorDialog = showPublishErrorDialog.value,
            dismissRenderBlocksErrorDialog = { showRenderBlocksErrorDialog.value = false },
            showPublishProgressDialog = showPublishProgressDialog.value,
            determineLocation = determineLocation
        )
    }
    else { // рекурсивная обработка окна для выбора подкатегории

        val categoryState by categoriesViewModel.categoryStateHolder.categoryState.collectAsState()

        var renderDraftAlert by remember { mutableStateOf(false) } // условие рендера окна черновика
        var tempSubCategory by remember { mutableStateOf<CategoryField.SubCategory?>(null) } // передача состояния категории между кнопками

        val onExit2 = {
            globalNavController.navigateUp()
        }

        // навигация на кнопку "Продолжить заполнение"
        val continuePostButton: () -> Unit = {
            if (tempSubCategory != null) {
                categoriesViewModel.handleEvent(CategoryEvent.UpdateDraftParams(categoryState.drafts[tempSubCategory!!.name]!!))
                categoriesViewModel.handleEvent(CategoryEvent.ClearDraft(tempSubCategory!!.name))
                globalNavController.navigate(PostRoutes.PostCreate(tempSubCategory!!)) {
                    launchSingleTop = true
                    restoreState = true
                }
                renderDraftAlert = false
            }
        }

        // навигация на кнопку "Создать новое"
        val createNewPostButton: () -> Unit = {
            if (tempSubCategory != null) {
                categoriesViewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(categoryState.tempDraft.categoryName, tempSubCategory!!.name)))
                categoriesViewModel.handleEvent(CategoryEvent.ClearDraft(tempSubCategory!!.name))
                globalNavController.navigate(PostRoutes.PostCreate(tempSubCategory!!)) {
                    launchSingleTop = true
                    restoreState = true
                }
                renderDraftAlert = false
            }
        }

        // навигация с проверкой наличия черновика
        val onSubCategoryClick: (CategoryField.SubCategory) -> Unit = { subsubcategory ->

            if (categoryState.drafts.containsKey(subsubcategory.name)) {
                tempSubCategory = subsubcategory
                renderDraftAlert = true
            }
            else {
                categoriesViewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(subsubcategory.name, subsubcategory.id)))
                globalNavController.navigate(PostRoutes.PostCreate(subsubcategory)) {
                    launchSingleTop = false // false - создаёт новый экземпляр экрана в бекстеке
                    restoreState = true
                }
            }
        }

        // условие рендера диалогового окна
        if (renderDraftAlert) {
            DraftDialog(tempSubCategory!!.name, createNewPostButton, continuePostButton) { renderDraftAlert = false }
        }

        SubCategoryChildrenContent(onExit2, subcategory, onSubCategoryClick)
    }

    // --- New LaunchedEffect for field content validation and publish ---
    LaunchedEffect(isPublishTriggered) {
        if (isPublishTriggered) {
            delay(100) // Даем системе обновить состояние после triggerCreateAdEvent
            val missingFields = subcategory.fields
                .filterIsInstance<CategoryField.MetaTag>()
                .flatMap { it.fields }
                .filter { field ->
                    val required = when (field) {
                        is CategoryField.TitleField -> field.isRequired
                        is CategoryField.PriceField -> field.isRequired
                        is CategoryField.DescriptionField -> field.isRequired
                        is CategoryField.TextField -> field.isRequired
                        is CategoryField.DropdownField -> field.isRequired
                        is CategoryField.NumberField -> field.isRequired
                        is CategoryField.LocationField -> field.isRequired
                        is CategoryField.PhotoPickerField -> field.isRequired
                        else -> false
                    }
                    if (!required) return@filter false
                    when (field) {
                        is CategoryField.TitleField -> draft.name.isBlank()
                        is CategoryField.PriceField -> {
                            val price = draft.price.toDoubleOrNull()
                            price == null || price < 1 // минимальная цена для объявления
                        }
                        is CategoryField.DescriptionField -> draft.description.isBlank()
                        is CategoryField.TextField -> draft.options[field.key] == null
                        is CategoryField.DropdownField -> draft.options[field.key] == null
                        is CategoryField.NumberField -> draft.options[field.key] == null
                        is CategoryField.LocationField -> (draft.location.fullText.isBlank() || draft.location.fullText == "Не установлено") //draft.options[field.key] == null
                        is CategoryField.PhotoPickerField -> draft.photos.all { it == null }
                        else -> false
                    }
                }

            if (missingFields.isNotEmpty()) {
                showRenderBlocksErrorDialog.value = true
                val firstMissingFieldKey = missingFields.firstOrNull()?.let { first ->
                    when (first) {
                        is CategoryField.TitleField -> first.key
                        is CategoryField.PriceField -> "${first.key} - минимум 1 руб."
                        is CategoryField.DescriptionField -> first.key
                        is CategoryField.TextField -> first.key
                        is CategoryField.DropdownField -> first.key
                        is CategoryField.NumberField -> first.key
                        is CategoryField.LocationField -> first.key
                        is CategoryField.PhotoPickerField -> "Фотографии"
                        else -> ""
                    }
                } ?: ""
                categoriesViewModel.categoryStateHolder.updateLastErrorField(firstMissingFieldKey)
            } else {
                Log.d("TEST", draftPost.tempDraft.data.toString())
                profileViewModel.handleEvent(ProfileEvent.AddPublication(draftPost.tempDraft))
                //categoriesViewModel.handleEvent(CategoryEvent.PublishPost)
                showPublishProgressDialog.value = true
                val result = categoriesViewModel.publish()
                showPublishProgressDialog.value = false

                if (result) {
                    globalNavController.navigate(MainRoutes.MainScreen.route) {
                        popUpTo(globalNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                else {
                    showPublishErrorDialog.value = true
                }
            }

            isPublishTriggered = false
        }
    }
}

@Composable
private fun PostCreateContent(
    onExit: () -> Boolean,
    subcategory: CategoryField.SubCategory,
    updateDraft: (PostData) -> Unit,
    onPublish: () -> Unit,
    draft: PostData,
    uploadPhoto: (Int, Uri, (Boolean) -> Unit) -> Unit,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit,
    lastErrorField: String,
    showRenderBlocksErrorDialog: Boolean,
    showPublishProgressDialog: Boolean,
    showPublishErrorDialog: Boolean,
    dismissRenderBlocksErrorDialog: () -> Unit,
    dismissPublishErrorDialog: () -> Unit,
    determineLocation: () -> Unit
) {

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        containerColor = Color.White,
        topBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onExit()
                        }
                )
                Space()
            }

        },
        bottomBar = {
            Button(
                onClick = {
                    onPublish()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                    .imePadding()
            ) {
                Text("Опубликовать объявление", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->

        PredictiveBackHandler(true) { progress: Flow<BackEventCompat> ->
            try {
                progress.collect { backEvent -> }
                onExit()
            } catch (e: CancellationException) {
                Log.w("PostCreateSwipe", e)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .consumeWindowInsets(innerPadding)
                .background(Color.Transparent)
        ) {

            Space(10.dp)

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)) {
                AppTextTitle(text = subcategory.name)
                Space(20.dp)
            }

            LazyColumn {

                subcategory.fields.forEach { field ->
                    item {
                        if (field is CategoryField.MetaTag) {
                            MetaTag(
                                key = field.key,
                                fields = field.fields,
                                draft = draft,
                                observer = updateDraft,
                                uploadPhoto = uploadPhoto,
                                isRequiredCheckSubmitted = isRequiredCheckSubmitted,
                                showErrorMessage = showErrorMessage,
                                determineLocation = determineLocation
                            )
                        }
                    }
                }

            }

            // загрузка фотографии на сервер
            if (showPublishProgressDialog) {
                Dialog(
                    onDismissRequest = {}
                ) {
                    //(LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0f)

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp).background(Color.White).padding(13.dp),
                            color = Color.LightGray,
                            trackColor = Color.Black,
                        )
                    }
                }
            }

            // ошибка заполнения полей
            if (showRenderBlocksErrorDialog) {
                AlertDialog(
                    modifier = Modifier.wrapContentWidth().padding(0.dp),
                    onDismissRequest = dismissRenderBlocksErrorDialog,
                    confirmButton = {
                        Button(
                            onClick = dismissRenderBlocksErrorDialog,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Ок")
                        }
                    },
                    title = {
                        Text("Не заполнено обязательное поле", fontSize = 17.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Text("Заполните поле: $lastErrorField", fontSize = 14.sp, color = Color.Black)
                    },
                    containerColor = Color.White
                )
            }

            // ошибка публикации
            if (showPublishErrorDialog) {
                AlertDialog(
                    modifier = Modifier.wrapContentWidth().padding(0.dp),
                    onDismissRequest = dismissPublishErrorDialog,
                    confirmButton = {
                        Button(
                            onClick = dismissPublishErrorDialog,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Ок")
                        }
                    },
                    title = {
                        Text("Объявление не создано", fontSize = 17.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Text("Неизвестная ошибка", fontSize = 14.sp, color = Color.Black)
                    },
                    containerColor = Color.White
                )
            }

        }

    }

}

@Composable
fun SubCategoryChildrenContent(
    onExit: () -> Boolean,
    subcategory: CategoryField.SubCategory,
    onCategoryClick: (CategoryField.SubCategory) -> Unit
) {

    Scaffold(contentWindowInsets = WindowInsets(0), containerColor = Color.White,
        topBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onExit()
                        }
                )
                Space()
            }

        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            item { AppTextTitle(text = subcategory.name) }

            item { Space(20.dp) }

            subcategory.children.forEachIndexed{ index, categoryField ->

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .clickable { onCategoryClick(categoryField) }
                    ) {
                        Text(
                            text = categoryField.name,
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }

                    if (index != subcategory.children.size - 1) Divider()
                }

            }

        }

    }

}