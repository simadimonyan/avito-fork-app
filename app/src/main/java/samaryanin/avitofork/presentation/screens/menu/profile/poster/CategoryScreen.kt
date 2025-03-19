package samaryanin.avitofork.presentation.screens.menu.profile.poster

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import samaryanin.avitofork.R
import samaryanin.avitofork.domain.model.post.CategoryField
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryEvent
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryState
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.presentation.screens.menu.profile.poster.navigation.PostRoutes
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle

@Preview
@Composable
private fun CategoryPreview() {

    val sample = CategoryState(
        mutableListOf(
            CategoryField.Category("", "Тестовая категория 1", mutableListOf()),
            CategoryField.Category("", "Тестовая категория 2", mutableListOf()),
            CategoryField.Category("", "Тестовая категория 3", mutableListOf())
        ),
        mutableListOf(),
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
        globalNavController.navigate(PostRoutes.PostSubCategories(category))
    }

    CategoryContent(onExit, categories, onSubCategoryClick)
}

@Composable
private fun CategoryContent(
    onExit: () -> Boolean,
    categoryState: CategoryState,
    chooseSubCategory: (CategoryField.Category) -> Unit
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
        }
    }
}
