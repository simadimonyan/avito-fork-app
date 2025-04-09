package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import samaryanin.avitofork.R
import samaryanin.avitofork.core.ui.utils.components.utils.space.Divider
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.components.utils.text.AppTextTitle
import samaryanin.avitofork.feature.marketplace.domain.model.post.CategoryField
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data.CategoryEvent
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.navigation.PostRoutes

@Preview
@Composable
private fun SubCategoryPreview() {

    val sample = CategoryField.Category("", "Тестовая категория",
        mutableListOf(
            CategoryField.SubCategory("", "Тестовая подкатегория 1", mutableListOf()),
            CategoryField.SubCategory("", "Тестовая подкатегория 2", mutableListOf()),
            CategoryField.SubCategory("", "Тестовая подкатегория 3", mutableListOf())
        ))

    SubCategoryContent({ true }, sample, {})
}

@Composable
fun SubCategoryScreen(
    globalNavController: NavController,
    category: CategoryField.Category,
    viewModel: CategoryViewModel
) {

    val categoryState by viewModel.appStateStore.categoryStateHolder.categoryState.collectAsState()

    var renderDraftAlert by remember { mutableStateOf(false) } // условие рендера окна черновика
    var tempSubCategory by remember { mutableStateOf<CategoryField.SubCategory?>(null) } // передача состояния категории между кнопками

    val onExit = {
        globalNavController.navigateUp()
    }

    // навигация на кнопку "Продолжить заполнение"
    val continuePostButton: () -> Unit = {
        if (tempSubCategory != null) {
            viewModel.handleEvent(CategoryEvent.UpdateDraftParams(categoryState.drafts[tempSubCategory!!.name]!!))
            viewModel.handleEvent(CategoryEvent.ClearDraft(tempSubCategory!!.name))
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
            viewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(categoryState.tempDraft.category, tempSubCategory!!.name)))
            viewModel.handleEvent(CategoryEvent.ClearDraft(tempSubCategory!!.name))
            globalNavController.navigate(PostRoutes.PostCreate(tempSubCategory!!)) {
                launchSingleTop = true
                restoreState = true
            }
            renderDraftAlert = false
        }
    }

    // навигация с проверкой наличия черновика
    val onSubCategoryClick: (CategoryField.SubCategory) -> Unit = { subcategory ->
        if (categoryState.drafts.containsKey(subcategory.name)) {
            tempSubCategory = subcategory
            renderDraftAlert = true
        } else {
            viewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(categoryState.tempDraft.category, subcategory.name)))
            globalNavController.navigate(PostRoutes.PostCreate(subcategory)) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    // условие рендера диалогового окна
    if (renderDraftAlert) {
        DraftDialog(tempSubCategory!!.name, createNewPostButton, continuePostButton) { renderDraftAlert = false }
    }

    SubCategoryContent(onExit, category, onSubCategoryClick)

}

@Composable
private fun SubCategoryContent(
    onExit: () -> Boolean,
    category: CategoryField.Category,
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            AppTextTitle(text = category.name)

            Space(20.dp)

            category.subs.forEachIndexed{ index, categoryField ->

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

                if (index != category.subs.size - 1) Divider()

            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun DraftDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        DraftDialog("Тестовая подкатегория", {}, {}) {}
    }
}

@Composable
fun DraftDialog(
    draftSubCategory: String,

    createNewPostButton: () -> Unit,
    continuePostButton: () -> Unit,
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {

        Card(
            modifier = Modifier
                .wrapContentSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(10.dp),
        ) {

            Column(modifier = Modifier
                .wrapContentSize()
                .padding(top = 30.dp)) {

                Text(
                    text = "У вас есть черновик",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Space()

                Text(
                    text = "Вы ранее начали заполнять объявление в \"${draftSubCategory}\", " +
                            "вы можете продолжить заполнение или создать новое объявление.",
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = Color.Gray
                )

                Space(10.dp)
                Divider()

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    onClick = { continuePostButton() }
                ) {
                    Text(
                        text = "Продолжить заполнение",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }

                Divider()

                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    onClick = {
                        createNewPostButton()
                    }
                ) {
                    Text(
                        text = "Создать новое",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }

                Space(2.dp)

            }

        }

    }

}