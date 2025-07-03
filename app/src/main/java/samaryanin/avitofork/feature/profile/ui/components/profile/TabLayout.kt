package samaryanin.avitofork.feature.profile.ui.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.dimagor555.avito.category.domain.field.FieldData
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.shared.ui.theme.navigationSelected

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
    ProfileTabLayout(rememberPagerState(pageCount = {2}), mutableListOf(), mutableMapOf(
        "0" to mutableListOf(
            PostState("", "Легковая машина", PostData(name = "Домик", location = FieldData.AddressValue("Беларусь", "", 0.0, 0.0), price = "100 000", unit = "руб.")),
            PostState("", "Легковая машина", PostData(name = "Домик", location = FieldData.AddressValue("Россия", "", 0.0, 0.0), price = "100 000", unit = "руб.")),
        ),
    ))
}

@Composable
fun ProfileTabLayout(pagerState: PagerState, tabTitles: List<TabItem>, posts: Map<String, List<PostState>>) {

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Tabs
        TabRow(
            containerColor = Color.White,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = navigationSelected,
                    width = 150.dp
                )
            },
            divider = {
                HorizontalDivider(color = Color.Transparent, modifier = Modifier.shadow(1.dp))
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

    }
}
