package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostData
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Chat
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.MessageState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components.ChatItemList
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components.ChatTopBar
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components.FilterItemList
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.navigation.MessagesRoutes

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun MessagesPreview() {
    MessagesContent({})
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param navHostController глобальный контроллер навигации
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun MessagesScreen(navHostController: NavHostController, viewModel: MessagesViewModel) {

    // навигация в личные сообщения по чату
    val message: (String) -> Unit = { chatId ->
        navHostController.navigate(MessagesRoutes.MessagesDirect(chatId)) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    MessagesContent(message)
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param message callback функция навигации в личные сообщения
 */
@Composable
fun MessagesContent(message: (String) -> Unit) {
    val scrollState = rememberLazyListState()

    val isNextEnabled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 // при прокрутке LazyColumn
        }
    }

    var search: String by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            Card(
                modifier = Modifier.fillMaxWidth()
                    .then(
                        if (isNextEnabled)
                            Modifier.shadow(2.dp,
                                RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        else Modifier
                    ),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                ChatTopBar(search) { search = it }
                FilterItemList()
                Space(5.dp)
            }

            ChatItemList(scrollState, "myId", mutableListOf(
                Chat(
                    "",
                    "Поддержка AvitoFork",
                    mutableListOf(
                        Message(
                            "",
                            "anotherId",
                            "Рады вам помочь!",
                            "15:00",
                            MessageState.READ
                        )
                    ),
                ),
                Chat(
                    "",
                    "Иван",
                    mutableListOf(
                        Message(
                            "",
                            "myId",
                            "Когда можем созвониться?",
                            "17:37",
                            MessageState.READ
                        )
                    ),
                    PostState(
                        "",
                        "",
                        PostData(
                            "IPhone 15 Pro",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Алексей",
                    mutableListOf(
                        Message(
                            "",
                            "myId",
                            "Послезавтра готов забрать",
                            "10:13",
                            MessageState.SENT
                        )
                    ),
                    PostState(
                        "",
                        "",
                        PostData(
                            "Мангал для дачи",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Евгений",
                    mutableListOf(),
                    PostState(
                        "",
                        "",
                        PostData(
                            "Macbook 14 pro M1 1T 32GB ",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Поддержка AvitoFork",
                    mutableListOf(
                        Message(
                            "",
                            "anotherId",
                            "Рады вам помочь!",
                            "15:00",
                            MessageState.READ
                        )
                    ),
                ),
                Chat(
                    "",
                    "Иван",
                    mutableListOf(
                        Message(
                            "",
                            "myId",
                            "Когда можем созвониться?",
                            "17:37",
                            MessageState.READ
                        )
                    ),
                    PostState(
                        "",
                        "",
                        PostData(
                            "IPhone 15 Pro",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Алексей",
                    mutableListOf(
                        Message(
                            "",
                            "myId",
                            "Послезавтра готов забрать",
                            "10:13",
                            MessageState.SENT
                        )
                    ),
                    PostState(
                        "",
                        "",
                        PostData(
                            "Мангал для дачи",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Евгений",
                    mutableListOf(),
                    PostState(
                        "",
                        "",
                        PostData(
                            "Macbook 14 pro M1 1T 32GB ",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Поддержка AvitoFork",
                    mutableListOf(
                        Message(
                            "",
                            "anotherId",
                            "Рады вам помочь!",
                            "15:00",
                            MessageState.READ
                        )
                    ),
                ),
                Chat(
                    "",
                    "Иван",
                    mutableListOf(
                        Message(
                            "",
                            "myId",
                            "Когда можем созвониться?",
                            "17:37",
                            MessageState.READ
                        )
                    ),
                    PostState(
                        "",
                        "",
                        PostData(
                            "IPhone 15 Pro",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Алексей",
                    mutableListOf(
                        Message(
                            "",
                            "myId",
                            "Послезавтра готов забрать",
                            "10:13",
                            MessageState.SENT
                        )
                    ),
                    PostState(
                        "",
                        "",
                        PostData(
                            "Мангал для дачи",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                ),
                Chat(
                    "",
                    "Евгений",
                    mutableListOf(),
                    PostState(
                        "",
                        "",
                        PostData(
                            "Macbook 14 pro M1 1T 32GB ",
                            mutableListOf(),
                            "150 000",
                            "руб."
                        )
                    )
                )
            ), message)

        }

    }

}