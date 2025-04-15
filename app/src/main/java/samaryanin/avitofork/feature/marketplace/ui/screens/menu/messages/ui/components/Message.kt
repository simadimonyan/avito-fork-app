package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.core.ui.utils.components.utils.space.Divider
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.theme.greyButton
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostData
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Chat
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    Box {
        MessageItemList(mutableListOf(
            Chat(
                "",
                "Поддержка AvitoFork",
                mutableListOf(
                    Message(
                        "",
                        "Рады вам помочь!",
                        ""
                    )
                ),
            ),
            Chat(
                "",
                "Евгений",
                mutableListOf(
                    Message(
                        "",
                        "Рады вам помочь!",
                        ""
                    )
                ),
                PostState(
                    "",
                    "",
                    PostData(
                        "Macbook 14 pro M1",
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            )
        ))
    }
}

@Composable
fun MessageItemList(chats: List<Chat>) {
    LazyColumn {
        items(chats.size) { index ->
            MessageItem(chats[index])
            if (index < chats.size) Divider()
        }
    }
}

@Composable
fun MessageItem(chat: Chat) {

    val post = chat.postReference.data

    Box(
        modifier = Modifier.background(Color.Transparent)
            .wrapContentSize().fillMaxWidth().padding(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(greyButton),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${chat.profileName.toCharArray()[0]}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(modifier = Modifier.padding(start = 15.dp)) {

                Text(
                    text = chat.profileName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Space(2.dp)

                if (chat.messages.size > 1) {

                    Text(
                        text = chat.messages[chat.messages.size - 1].content,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )

                }
                else {

                    Text(
                        text = post.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )

                    Space(2.dp)

                    Text(
                        text = "${post.price} ${post.unit}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                }

            }

        }
    }

}