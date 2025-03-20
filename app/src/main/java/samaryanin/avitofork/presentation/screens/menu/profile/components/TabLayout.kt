package samaryanin.avitofork.presentation.screens.menu.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import samaryanin.avitofork.domain.model.post.PostData
import samaryanin.avitofork.domain.model.post.PostState
import samaryanin.avitofork.presentation.ui.components.placeholders.ProfileEmptyPublication
import samaryanin.avitofork.presentation.ui.components.placeholders.ProfilePublication
import samaryanin.avitofork.presentation.ui.components.utils.space.Space

sealed class TabItem(val index: Int, val title: String) {

    /**
     * Вкладка опубликованных объявлений в профиле
     */
    data object Publications : TabItem(0, "Объявления")

    /**
     * Вкладка архивных объявлений в профиле
     */
    data object Archive : TabItem(1, "Архив")

}

@Preview
@Composable
fun ProfileTabLayoutPreview() {
    ProfileTabLayout(mutableMapOf(
        "0" to mutableListOf(
            PostState("", "Легковая машина", PostData(description = "Очень нереально круто", price = "100 000", unit = "руб.")),
            PostState("", "Легковая машина", PostData(description = "Очень нереально круто", price = "100 000", unit = "руб.")),
            PostState("", "Легковая машина", PostData(description = "Очень нереально круто", price = "100 000", unit = "руб."))
        ),
    ))
}

@Composable
fun ProfileTabLayout(posts: Map<String, List<PostState>>) {

    val tabTitles = listOf(TabItem.Publications, TabItem.Archive)
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.windowInsetsPadding(WindowInsets(0))) {
        // Tabs
        TabRow(
            containerColor = Color.White,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Black,
                    width = 150.dp
                )
            },
            divider = {
                HorizontalDivider(color = Color.Transparent, modifier = Modifier.shadow(3.dp))
            },
            selectedTabIndex = pagerState.currentPage
        ) {
            tabTitles.forEachIndexed { index, tab ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    text = {
                        Text(
                            text = tab.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.Black else Color.Gray // Серый для неактивных
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true
        ) { page ->

            if (posts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileEmptyPublication()
                        Space()
                        ProfileEmptyPublication()
                        Space()
                        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(
                                modifier = Modifier.padding(bottom = 70.dp),
                                text = "У вас нет объявлений",
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {

                LazyColumn(modifier = Modifier.background(Color.White)) {

                    item { Space(5.dp) }

                    if (page == 0) {

                        items(posts["0"]!!.size) {
                            posts["0"]!!.forEach { field ->
                                Surface(modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp), color = Color.White, shape = RoundedCornerShape(10.dp), shadowElevation = 2.dp) {
                                    ProfilePublication(
                                        title = field.subcategory,
                                        description = field.data.description,
                                        price = field.data.price + " " + field.data.unit
                                    )
                                }
                            }
                        }

                    }

                }

            }

        }
    }
}

