package samaryanin.avitofork.presentation.screens.menu.profile.poster

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import samaryanin.avitofork.R
import samaryanin.avitofork.domain.model.post.CategoryField
import samaryanin.avitofork.domain.model.post.PostData
import samaryanin.avitofork.domain.model.post.PostState
import samaryanin.avitofork.presentation.navigation.MainRoutes
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileEvent
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.presentation.screens.menu.profile.poster.components.MetaTag
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryEvent
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle

@Preview
@Composable
private fun PostCreatePreview() {

    val sample = CategoryField.SubCategory("", "Тестовая подкатегория",
        mutableListOf(
            CategoryField.MetaTag(
                key = "",
                fields = mutableListOf(
                    CategoryField.PhotoPickerField("", 8),
                    CategoryField.TextField("Описание:", ""),
                    CategoryField.TextField("Описание 1:", ""),
                )
            ),
            CategoryField.MetaTag(
                key = "Характеристики 2",
                fields = mutableListOf(
                    CategoryField.NumberField("Год выпуска:", "", "г"),
                    CategoryField.NumberField("Объем двигателя:", "", "л")
                )
            ),
            CategoryField.MetaTag(
                key = "Характеристики 3",
                fields = mutableListOf(
                    CategoryField.DropdownField("Тип недвижимости:", "Не выбран", mutableListOf(), true),
                    CategoryField.LocationField("Местоположение строения")
                )
            )
        )
    )

    PostCreateContent({ true }, sample, {}, {}, PostData())
}

@Composable
fun PostCreateScreen(
    globalNavController: NavController,
    subcategory: CategoryField.SubCategory,
    categoriesViewModel: CategoryViewModel,
    profileViewModel: ProfileViewModel,
) {

    val draftPost by categoriesViewModel.appStateStore.categoryStateHolder.categoryState.collectAsState()

    val onExit = {
        globalNavController.navigateUp()
    }

    val onPublish: () -> Unit = {
        Log.d("TEST", draftPost.tempDraft.data.toString())

        // TODO -- заменить на ивент для серверного апдейта
        profileViewModel.handleEvent(ProfileEvent.AddPublication(draftPost.tempDraft))

        categoriesViewModel.handleEvent(CategoryEvent.PublishPost)

        globalNavController.navigate(MainRoutes.MainScreen.route) {
            popUpTo(globalNavController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val updateDraft: (PostData) -> Unit = { data ->
        categoriesViewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(draftPost.tempDraft.category, draftPost.tempDraft.subcategory, data)))
    }

    PostCreateContent(onExit, subcategory, updateDraft, onPublish, draftPost.tempDraft.data)
}

@Composable
private fun PostCreateContent(
    onExit: () -> Boolean,
    subcategory: CategoryField.SubCategory,
    updateDraft: (PostData) -> Unit,
    onPublish: () -> Unit,
    data: PostData,
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .consumeWindowInsets(innerPadding)
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
                            MetaTag(key = field.key, fields = field.fields, updateDraft, data)
                        }
                    }
                }

            }
        }



    }

}