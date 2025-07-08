package samaryanin.avitofork.feature.profile.ui.feature.profile.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.feature.auth.ui.state.AuthState
import samaryanin.avitofork.feature.profile.ui.components.profile.AddProfile
import samaryanin.avitofork.feature.profile.ui.components.profile.DefaultAvatar
import samaryanin.avitofork.feature.profile.ui.components.profile.ProfileTabLayout
import samaryanin.avitofork.feature.profile.ui.components.profile.TabItem
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileState
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel
import samaryanin.avitofork.shared.ui.components.placeholders.ProfileEmptyPublication
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.veryLightGray

/**
 * Состояние экрана профиля когда пользователь авторизован
 */
@Composable
fun ProfileAuthorized(
    scrollState: ScrollState,
    profileState: () -> ProfileState,
    authState: () -> AuthState,
    navHostController: NavHostController,
    // navigateTo: (Int) -> Unit
) {

    val tabTitles = listOf(TabItem.Publications, TabItem.Archive)
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val posts = profileState().posts
    val vm: ProfileViewModel = hiltViewModel()

    var userAds = vm.userAds.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        // first element for scroll & padding
        Space(2.dp)
        Space(28.dp)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(0),
        ) {
            var name = authState.invoke().profile
            name = if (name != "") name else "Тестовое имя"

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultAvatar(name = name)
                Space()
                AddProfile()
            }

            Space(10.dp)

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = name,
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Space()

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "На Avito Fork с 2010 года",
                fontSize = 15.sp,
                color = Color.Black,
            )

            Space(2.dp)

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Частное лицо",
                fontSize = 15.sp,
                color = Color.Black,
            )

            Space(2.dp)

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "ID: 123456789",
                fontSize = 15.sp,
                color = Color.Black,
            )

            Space()
        }

        ProfileTabLayout(pagerState, tabTitles, profileState().posts)

        HorizontalPager(
            modifier = Modifier.background(veryLightGray),
            state = pagerState,
            userScrollEnabled = true,
            beyondViewportPageCount = 0,
            verticalAlignment = Alignment.Top
        ) { page ->

            if (page == 0) { // таб объявлений

                if (userAds.value.isEmpty()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            Modifier.padding(vertical = 120.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ProfileEmptyPublication()
                            Space()
                            ProfileEmptyPublication()
                            Space()
                            Text(
                                text = "У вас нет объявлений",
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {

                    LazyVerticalGrid(
                        modifier = Modifier
                            .heightIn(max = 10000.dp) // костыль для вложенности скролов
                            .padding(bottom = 100.dp),
                        columns = GridCells.Adaptive(minSize = 200.dp),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        userScrollEnabled = false
                    ) {

                        items(userAds.value) { userAd ->

                            Column {
                                Surface(
                                    modifier = Modifier.padding(10.dp),
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp),
                                    shadowElevation = 2.dp
                                ) {
                                    UserAdsCard(
                                        ad = userAd,
                                        isAuthorized = true,
                                        onFavoriteClick = {},
                                        globalNavController = navHostController
                                    )
//                                    ProfilePublication(
//                                        title = field.data.name,
//                                        location = field.data.location.fullText,
//                                        price = field.data.price + " " + field.data.unit,
//                                    )
                                }
                            }

                        }

                    }

                }

            } else { // таб архив

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        Modifier.padding(vertical = 120.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileEmptyPublication()
                        Space()
                        ProfileEmptyPublication()
                        Space()
                        Text(
                            text = "Ваш архив пуст",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

        }

        // ------------

    }

//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        FloatingActionButton(
//            onClick = {
//                navigateTo(2) // 2 - индекс навигации на меню управления объявлениями
//            },
//            modifier = Modifier
//                .padding(bottom = 70.dp)
//                .align(Alignment.BottomEnd),
//            containerColor = navigationSelected
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
//        }
//    }

}
