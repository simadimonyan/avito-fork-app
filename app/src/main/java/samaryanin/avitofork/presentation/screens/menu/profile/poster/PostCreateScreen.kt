package samaryanin.avitofork.presentation.screens.menu.profile.poster

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import samaryanin.avitofork.presentation.screens.menu.profile.poster.components.MetaTag
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

    PostCreateContent({ true }, sample, {})
}

@Composable
fun PostCreateScreen(
    globalNavController: NavController,
    subcategory: CategoryField.SubCategory,
) {

    val onExit = {
        globalNavController.navigateUp()
    }

    val onPublish = {
        //TODO (логика опубликования объявления)
    }

    PostCreateContent(onExit, subcategory, onPublish)
}

@Composable
private fun PostCreateContent(
    onExit: () -> Boolean,
    subcategory: CategoryField.SubCategory,
    onPublish: () -> Unit,
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
                    .padding(
                        bottom = maxOf(
                            WindowInsets.ime
                                .asPaddingValues()
                                .calculateBottomPadding() - WindowInsets.navigationBars
                                .asPaddingValues()
                                .calculateBottomPadding(),
                            0.dp
                        )
                    )
            ) {
                Text("Опубликовать объявление", fontSize = 15.sp)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Space(10.dp)

            Box(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
                AppTextTitle(text = subcategory.name)
                Space(20.dp)
            }

            LazyColumn {

                subcategory.fields.forEach { field ->
                    item {
                        if (field is CategoryField.MetaTag) {
                            MetaTag(key = field.key, fields = field.fields)
                        }
                    }
                }

            }
        }



    }

}