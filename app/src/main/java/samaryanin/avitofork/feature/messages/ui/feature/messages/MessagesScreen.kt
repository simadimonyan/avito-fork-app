package samaryanin.avitofork.feature.messages.ui.feature.messages

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
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
import samaryanin.avitofork.feature.messages.domain.models.Chat
import samaryanin.avitofork.feature.messages.domain.models.Message
import samaryanin.avitofork.feature.messages.domain.models.MessageState
import samaryanin.avitofork.feature.messages.ui.feature.chat.components.ChatItemList
import samaryanin.avitofork.feature.messages.ui.feature.chat.components.ChatTopBar
import samaryanin.avitofork.feature.messages.ui.feature.messages.components.FilterItemList
import samaryanin.avitofork.feature.messages.ui.navigation.MessagesRoutes
import samaryanin.avitofork.feature.messages.ui.state.MessagesEvent
import samaryanin.avitofork.feature.messages.ui.state.MessagesState
import samaryanin.avitofork.feature.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.shared.ui.components.utils.space.Space

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun MessagesPreview() {
    MessagesContent({}, MessagesState(
        mutableListOf(
            Chat(
                "",
                "Поддержка AvitoFork",
                mutableListOf(
                    Message(
                        "",
                        "anotherId",
                        "Рады вам помочь!",
                        "1745406402992",
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
                        "1745406402992",
                        MessageState.READ
                    )
                ),
                PostState(
                    "",
                    "",
                    PostData(
                        "IPhone 15 Pro",
                        mutableMapOf(),
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
                        "1745406402992",
                        MessageState.SENT
                    )
                ),
                PostState(
                    "",
                    "",
                    PostData(
                        "Мангал для дачи",
                        mutableMapOf(),
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
                        mutableMapOf(),
                        "150 000",
                        "руб."
                    )
                )
            )
        )
    ))
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param navHostController глобальный контроллер навигации
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun MessagesScreen(navHostController: NavHostController, viewModel: MessagesViewModel) {

    val chats by viewModel.appStateStore.messageStateHolder.messagesState.collectAsState()

    // обновление сообщений
    viewModel.handleEvent(MessagesEvent.ChatsRefresh)

    // навигация в личные сообщения по чату
    val message: (Chat) -> Unit = { chat ->
        navHostController.navigate(MessagesRoutes.MessagesDirect(chat.chatId)) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    MessagesContent(message, chats)
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 * @param message callback функция навигации в личные сообщения
 */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MessagesContent(message: (Chat) -> Unit, chats: MessagesState) {
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

            ChatItemList(scrollState, "myId", chats, message)

        }

    }

}