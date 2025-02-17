package samaryanin.avitofork.presentation.screens.menu.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
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
import samaryanin.avitofork.presentation.ui.components.placeholders.ProfileEmptyAdvertisement
import samaryanin.avitofork.presentation.ui.components.utils.space.Space

sealed class TabItem(val index: Int, val title: String) {

    /**
     * Вкладка опубликованных объявлений в профиле
     */
    object Publications : TabItem(0, "Объявления")

    /**
     * Вкладка архивных объявлений в профиле
     */
    object Archive : TabItem(1, "Архив")

}

@Preview
@Composable
fun ProfileTabLayout() {

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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    ProfileEmptyAdvertisement()
                    Space()
                    ProfileEmptyAdvertisement()
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

        }
    }
}