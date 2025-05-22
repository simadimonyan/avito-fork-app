package samaryanin.avitofork.feature.messages.ui.feature.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.messages.domain.models.Chat
import samaryanin.avitofork.feature.messages.domain.models.Message
import samaryanin.avitofork.feature.messages.domain.models.MessageState
import samaryanin.avitofork.feature.messages.ui.state.MessagesState
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.shared.ui.components.utils.space.Divider
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.adaptive.LocaleDimensions
import samaryanin.avitofork.shared.ui.theme.greyButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    Box {
        ChatItemList(
            rememberLazyListState(), "myId", MessagesState(
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
                                "1745406425571",
                                MessageState.READ
                            )
                        ),
                        PostState(
                            "",
                            "",
                            PostData(
                                "IPhone 15 Pro",
                                mutableMapOf(),
                                "1745406402992",
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
                                "1745406425571",
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
            )
        ) {}
    }
}

@Composable
fun ChatItemList(
    state: LazyListState = rememberLazyListState(),
    userId: String,
    chatsState: MessagesState,
    onItemClicked: (Chat) -> Unit,
) {
    LazyColumn(state = state) {

        item { Space(1.dp) }

        item { Space(84.dp) }

        items(chatsState.chats.size) { index ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White)
                    .clickable { onItemClicked(chatsState.chats[index]) }
            ) {
                ChatItem(userId, chatsState.chats[index])
                if (index != 0 && index < chatsState.chats.size - 1) Divider()
            }

        }

        item {
            Space(40.dp)
        }
    }
}

@Composable
fun ChatItem(
    userId: String,
    chat: Chat
) {

    val LocalDimensions = LocaleDimensions.current
    val post = chat.postReference.data

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .wrapContentSize()
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(LocalDimensions.Messages.IconSize.iconSizeChatAvatar)
                    .clip(CircleShape)
                    .background(greyButton),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${chat.profileName.toCharArray()[0]}",
                    fontSize = (LocalDimensions.Messages.IconSize.iconSizeChatAvatar / 2).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(modifier = Modifier.padding(start = 15.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = chat.profileName,
                        fontSize = LocalDimensions.Messages.FontSize.fontSizeProfileName,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(Modifier.weight(1f))

                    if (chat.messages.isNotEmpty()) {

                        val lastMessage = chat.messages[chat.messages.size - 1]

                        Text(
                            text = SimpleDateFormat("HH:mm", Locale.getDefault())
                                .format(Date(lastMessage.timestamp.toLong())),
                            fontSize = LocalDimensions.Messages.FontSize.fontSizeTimestamp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )

                        Space(5.dp)

                    }

                }

                if (post.name.isNotBlank()) {

                    Space(1.dp)

                    val postName = if (post.name.length > 25) {
                        "${post.name.take(25)}..."
                    } else {
                        post.name
                    }

                    Text(
                        text = postName,
                        fontSize = LocalDimensions.Messages.FontSize.fontSizeAdName,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )

                }

                if (chat.messages.isNotEmpty()) {

                    Space(if (post.name.isNotBlank()) 2.dp else 4.dp)

                    Row {

                        val lastMessage = chat.messages[chat.messages.size - 1]

                        val message = if (lastMessage.content.length > 20) {
                            "${lastMessage.content.take(20)}..."
                        } else {
                            lastMessage.content
                        }

                        Text(
                            text = if (userId == lastMessage.user) "Вы: $message"
                                else message,
                            fontSize = LocalDimensions.Messages.FontSize.fontSizeLastMessage,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        if (lastMessage.user == userId) {

                            chat.messages[chat.messages.size - 1].state.let { state ->

                                var messageStateResource = 0
                                var size = 0
                                var offset = 0
                                var readFlag = false

                                when (state) {

                                    MessageState.RECEIVED -> {
                                        messageStateResource = R.drawable.received_mark
                                        readFlag = false
                                        size = 15
                                        offset = 0
                                    }

                                    MessageState.SENT -> {
                                        messageStateResource = R.drawable.sent_mark
                                        readFlag = false
                                        size = 12
                                        offset = 3
                                    }

                                    MessageState.READ -> {
                                        messageStateResource = R.drawable.received_mark
                                        readFlag = true
                                        size = 15
                                        offset = 0
                                    }

                                }

                                Icon(
                                    painter = painterResource(messageStateResource),
                                    tint = if (readFlag) Color.Blue else Color.Black,
                                    contentDescription = "mark",
                                    modifier = Modifier
                                        .size(size.dp)
                                        .offset(-offset.dp, 3.dp)
                                )

                            }

                        }

                        Space(5.dp)

                    }

                }
                else {

                    Space(2.dp)

                    Text(
                        text = "${post.price} ${post.unit}",
                        fontSize = LocalDimensions.Messages.FontSize.fontSizePrice,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                }

            }

        }

    }

}