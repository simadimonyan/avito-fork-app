package samaryanin.avitofork.presentation.screens.menu.profile.poster

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import samaryanin.avitofork.R
import samaryanin.avitofork.domain.model.post.CategoryField
import samaryanin.avitofork.presentation.screens.menu.profile.poster.navigation.PostRoutes
import samaryanin.avitofork.presentation.ui.components.utils.space.Divider
import samaryanin.avitofork.presentation.ui.components.utils.space.Space
import samaryanin.avitofork.presentation.ui.components.utils.text.AppTextTitle

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
) {

    val onExit = {
        globalNavController.navigateUp()
    }

    val onSubCategoryClick: (CategoryField.SubCategory) -> Unit = { subcategory ->
        globalNavController.navigate(PostRoutes.PostCreate(subcategory)) {
            launchSingleTop = true
            restoreState = true
        }
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