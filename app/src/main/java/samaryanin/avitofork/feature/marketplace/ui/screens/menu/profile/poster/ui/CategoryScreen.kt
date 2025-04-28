package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.ui

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import samaryanin.avitofork.R
import samaryanin.avitofork.core.ui.utils.components.utils.space.Divider
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.components.utils.text.AppTextTitle
import samaryanin.avitofork.feature.marketplace.domain.model.post.CategoryField
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.ui.data.CategoryEvent
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.ui.data.CategoryState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.ui.data.CategoryViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.navigation.PostRoutes

@Preview
@Composable
private fun CategoryPreview() {

    val sample = CategoryState(
        mutableListOf(
            CategoryField.Category("", "Тестовая категория 1", mutableListOf()),
            CategoryField.Category("", "Тестовая категория 2", mutableListOf()),
            CategoryField.Category("", "Тестовая категория 3", mutableListOf())
        ),
        mutableMapOf(),
        PostState(),
        false
    )

    CategoryContent({ true }, sample, {})
}

@Composable
fun CategoryScreen(globalNavController: NavController, viewModel: CategoryViewModel = hiltViewModel()) {

    viewModel.handleEvent(CategoryEvent.UpdateCategoryListConfiguration)

    val categories by viewModel.appStateStore.categoryStateHolder.categoryState.collectAsState()

    Log.d("LOADED", "CategoryScreen: Categories loaded: ${categories.categories}")

    val onExit = {
        globalNavController.navigateUp()
    }

    val onSubCategoryClick: (CategoryField.Category) -> Unit = { category ->
        viewModel.handleEvent(CategoryEvent.UpdateDraftParams(PostState(category.name)))
        globalNavController.navigate(PostRoutes.PostSubCategories(category))
    }

    CategoryContent(onExit, categories, onSubCategoryClick)
}

@Composable
private fun CategoryContent(
    onExit: () -> Boolean,
    categoryState: CategoryState,
    chooseSubCategory: (CategoryField.Category) -> Unit,
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
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
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

            AppTextTitle(text = "Новое объявление")

            Space(20.dp)

            categoryState.categories.forEachIndexed{ index, categoryField ->

                when (categoryField) {

                    is CategoryField.Category -> {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .clickable {
                                    Log.d("CLICK", "CategoryContent: Category clicked: ${categoryField.name + " " + categoryField.subs}")
                                    chooseSubCategory(categoryField)
                                }
                        ) {
                            Text(
                                text = categoryField.name,
                                color = Color.Black,
                                fontSize = 20.sp,
                            )
                        }

                        if (index != categoryState.categories.size - 1) Divider()

                    }
                    else -> Unit

                }

            }
        }

    }

}

