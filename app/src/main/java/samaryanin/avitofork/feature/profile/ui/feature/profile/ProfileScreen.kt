package samaryanin.avitofork.feature.profile.ui.feature.profile

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import samaryanin.avitofork.R
import samaryanin.avitofork.app.activity.data.AppEvent
import samaryanin.avitofork.app.activity.data.AppState
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.feature.auth.ui.state.AuthState
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.feature.profile.ui.components.AddProfile
import samaryanin.avitofork.feature.profile.ui.components.DefaultAvatar
import samaryanin.avitofork.feature.profile.ui.components.ProfileTabLayout
import samaryanin.avitofork.feature.profile.ui.components.TabItem
import samaryanin.avitofork.feature.profile.ui.navigation.profile.ProfileRoutes
import samaryanin.avitofork.feature.profile.ui.navigation.settings.SettingsRoutes
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileState
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel
import samaryanin.avitofork.shared.ui.components.placeholders.ProfileEmptyPublication
import samaryanin.avitofork.shared.ui.components.placeholders.ProfilePublication
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.components.utils.text.AppTextTitle
import samaryanin.avitofork.shared.ui.theme.alphaLightBlue
import samaryanin.avitofork.shared.ui.theme.lightBlue
import samaryanin.avitofork.shared.ui.theme.navigationSelected
import samaryanin.avitofork.shared.ui.theme.veryLightGray

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun ProfilePreview() {
    ProfileContent({ AppState() }, {}, {}, { AuthState() }) { ProfileState() }
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param profileViewModel модуль обработки профиля
 * @param mainViewModel модель глобальной обработки состояний приложения
 * @param globalNavController глобальный контроллер навигации
 */
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    mainViewModel: MainViewModel,
    globalNavController: NavController
) {

    val appState by mainViewModel.appStateStore.appStateHolder.appState.collectAsState()
    val authState by mainViewModel.appStateStore.authStateHolder.authState.collectAsState()
    val profileState by profileViewModel.appStateStore.profileStateHolder.profileState.collectAsState()

    val navigateTo = { index: Int ->
        when (index) {
            0 -> { // 0 - индекс навигации на экран уведомлений
                globalNavController.navigate(ProfileRoutes.Notifications.route) {
                    popUpTo(globalNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
            1 -> { // 1 - индекс навигации на экран настроек
                globalNavController.navigate(SettingsRoutes.Settings.route) {
                    popUpTo(globalNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        }
    }

    // обработчик событий для AuthBottomSheet
    val authRequest = {
        mainViewModel.handleEvent(AppEvent.ToggleAuthRequest)
    }

    ProfileContent({ appState }, navigateTo, authRequest, { authState }, { profileState })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    appState: () -> AppState,
    navigateTo: (Int) -> Unit,
    authRequest: () -> Unit,
    authState: () -> AuthState,
    profileState: () -> ProfileState
) {

    val scrollState = rememberScrollState()

    val isNextEnabled by remember {
        derivedStateOf {
            scrollState.value > 0 // при прокрутке LazyColumn
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        contentColor = Color.White,
        containerColor = Color.White,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            if (appState().isLoggedIn) {
                ProfileAuthorized(scrollState, profileState, authState)
            } else {
                ProfileUnauthorized(authRequest)
            }

            Card(
                modifier = Modifier.fillMaxWidth().then(
                    if (isNextEnabled)
                        Modifier.shadow(2.dp,
                            RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    else Modifier
                ),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                TopAppBar(
                    modifier = Modifier,
                    windowInsets = WindowInsets(0),
                    title = {
                        AppTextTitle("Профиль")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    actions = {
                        IconButton(onClick = {
                            navigateTo(0) // 0 - индекс навигации на экран уведомлений
                        }) {
                            Icon(
                                modifier = Modifier.size(26.dp),
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Notifications",
                                tint = navigationSelected
                            )
                        }
                        IconButton(onClick = {
                            navigateTo(1) // 1 - индекс навигации на экран настроек
                        }) {
                            Icon(
                                modifier = Modifier.size(26.dp),
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                tint = navigationSelected
                            )
                        }
                    }
                )
            }
        }
    }
}

/**
 * Состояние экрана профиля когда пользователь авторизован
 */
@Composable
fun ProfileAuthorized(
    scrollState: ScrollState,
    profileState: () -> ProfileState,
    authState: () -> AuthState,
    //navigateTo: (Int) -> Unit
) {

    val tabTitles = listOf(TabItem.Publications, TabItem.Archive)
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val posts = profileState().posts

    var cards = if (posts.isEmpty()) mutableListOf<PostState>() else posts["0"]!!

//    cards = mutableListOf(
//        PostState("", "Легковая машина", PostData(name = "Домик", location = "Беларусь", price = "100 000", unit = "руб.")),
//        PostState("", "Легковая машина", PostData(name = "Домик", location = "Беларусь", price = "100 000", unit = "руб.")),
//        PostState("", "Легковая машина", PostData(name = "Домик", location = "Беларусь", price = "100 000", unit = "руб.")),
//        PostState("", "Легковая машина", PostData(name = "Домик", location = "Беларусь", price = "100 000", unit = "руб.")),
//        PostState("", "Легковая машина", PostData(name = "Домик", location = "Беларусь", price = "100 000", unit = "руб.")),
//        PostState("", "Легковая машина", PostData(name = "Домик", location = "Беларусь", price = "100 000", unit = "руб.")),
//    )

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
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

            Row(modifier = Modifier
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

                if (cards.isEmpty()) {

                    Row(modifier = Modifier.fillMaxWidth().height(500.dp), horizontalArrangement = Arrangement.Center) {
                        Column(Modifier.padding(vertical = 120.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
                }
                else {

                    LazyVerticalGrid(
                        modifier = Modifier
                            .heightIn(max = 10000.dp) // костыль для вложенности скролов
                            .padding(bottom = 100.dp),
                        columns = GridCells.Adaptive(minSize = 200.dp),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        userScrollEnabled = false
                    ) {

                        items(cards) { field ->

                            Column {
                                Surface(
                                    modifier = Modifier.padding(10.dp),
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp),
                                    shadowElevation = 2.dp
                                ) {
                                    ProfilePublication(
                                        title = field.data.name,
                                        location = field.data.location,
                                        price = field.data.price + " " + field.data.unit,
                                    )
                                }
                            }

                        }

                    }

                }

            }
            else { // таб архив

                Row(modifier = Modifier.fillMaxWidth().height(500.dp), horizontalArrangement = Arrangement.Center) {
                    Column(Modifier.padding(vertical = 120.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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

/**
 * Состояние экрана профиля когда пользователь неавторизован
 */
@Composable
fun ProfileUnauthorized(authRequest: () -> Unit) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = LocalContext.current.getString(R.string.unauthorized_profile),
            fontSize = 15.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        Space()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp)
                .height(40.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = alphaLightBlue),
            onClick = {
                authRequest()
            },
        ) {
            Text(
                text = "Войти или зарегистрироваться",
                fontSize = 15.sp,
                color = lightBlue,
                fontWeight = FontWeight.Normal
            )
        }

    }

}