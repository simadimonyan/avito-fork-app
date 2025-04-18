package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import samaryanin.avitofork.R
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.theme.lightBlueColor
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.MessageState

@Preview(showBackground = true)
@Composable
fun MessageItemPreview() {
    Box(Modifier.padding(10.dp)) {
        MessageItemList("myId", mutableListOf(
            Message(
                "",
                "myId",
                "Привет!",
                "10:10",
                MessageState.READ
            ),
            Message(
                "",
                "myId",
                "Как дела?",
                "10:10",
                MessageState.READ
            ),
            Message(
                "",
                "anotherId",
                "Привет!",
                "10:12",
                MessageState.SENT
            ),
            Message(
                "",
                "anotherId",
                "Все хорошо!",
                "10:12",
                MessageState.SENT
            ),
            Message(
                "",
                "myId",
                "Супер!",
                "10:14",
                MessageState.RECEIVED
            ),
            Message(
                "",
                "myId",
                "Рад за тебя!",
                "10:15",
                MessageState.SENT
            )
        ))
    }
}

/**
 * @param userId id авторизованного профиля
 * @param messages список сообщений
 */
@Composable
fun MessageItemList(userId: String, messages: MutableList<Message>) {
    LazyColumn {
        items(messages.size) { index ->

            // условие отличия сообщений авторизованного пользователя от других
            val personal = messages[index].user == userId

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (personal) Arrangement.End else Arrangement.Start) {
                    if (personal) {

                        // если следующее сообщение будет от авторизованного пользователя в ряд
                        val message = !(index + 1 < messages.size && messages[index + 1].user == userId)
                        MessageSentItem(messages[index], message)

                        if (!message) Space(4.dp)

                    }
                    else {

                        // если следующее сообщение будет от того же отправителя в ряд
                        val message = !(index + 1 < messages.size && messages[index].user == messages[index + 1].user)

                        if (!message) Space(4.dp)
                        MessageReceivedItem(messages[index], message)

                    }
                }
            }

            if (index < messages.size) Space(2.dp)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MessageSentItem(message: Message, tail: Boolean) {
    Row(verticalAlignment = Alignment.Bottom) {
        Card(
            modifier = Modifier.wrapContentSize().zIndex(2f)
                .shadow(10.dp, RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = if (tail) 0.dp else 10.dp,
                    bottomEnd = 10.dp),
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.LightGray
                ),
            colors = CardDefaults.cardColors(containerColor = lightBlueColor),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 10.dp,
                bottomEnd = if (tail) 0.dp else 10.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(15.dp, 10.dp, 5.dp, 10.dp)
                    .widthIn(max = 270.dp)
            ) {
                FlowRow(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = message.content,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left
                    )
                    Row(modifier = Modifier.align(Alignment.Bottom).offset(0.dp, (2).dp)) {
                        Space(2.dp)
                        Text(
                            text = message.timestamp,
                            fontSize = 10.sp,
                            color = Color.Gray,
                        )
                        Space(4.dp)
                        MessageState(message.state)
                    }
                }
            }
        }
        if (tail) {
            Canvas(
                modifier = Modifier.size(8.dp, 10.dp).offset((-2).dp).zIndex(1f)
            ) {
                val width = size.width
                val height = size.height

                val path = Path().apply {
                    moveTo(0f, height)
                    lineTo(width, height)
                    lineTo(width / 2f, height / 1.2f)
                    lineTo(0f, 0f)
                    close()
                }

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Gray.copy(alpha = 0.1f)
                    }

                    val shadowPath = path.copy()
                    shadowPath.translate(Offset(1f, 1f))

                    canvas.drawOutline(
                        outline = Outline.Generic(shadowPath),
                        paint = paint
                    )
                }

                drawIntoCanvas { canvas ->
                    canvas.drawOutline(
                        outline = Outline.Generic(path),
                        paint = Paint().apply {
                            color = lightBlueColor
                            pathEffect = PathEffect.cornerPathEffect(5f)
                        }
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MessageReceivedItem(message: Message, tail: Boolean) {
    Row(verticalAlignment = Alignment.Bottom) {

        if (tail) {
            Canvas(
                modifier = Modifier.size(8.dp, 10.dp).offset((2).dp)
            ) {
                val width = size.width
                val height = size.height

                val path = Path().apply {
                    moveTo(width, height)
                    lineTo(0f, height)
                    lineTo(width / 2f, height / 1.2f)
                    lineTo(width, 0f)
                    close()
                }

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Gray.copy(alpha = 0.07f)
                    }

                    val shadowPath = path.copy()
                    shadowPath.translate(Offset(1f, 1f))

                    canvas.drawOutline(
                        outline = Outline.Generic(shadowPath),
                        paint = paint
                    )
                }

                drawIntoCanvas { canvas ->
                    canvas.drawOutline(
                        outline = Outline.Generic(path),
                        paint = Paint().apply {
                            color = Color.White
                            pathEffect = PathEffect.cornerPathEffect(5f)
                        }
                    )
                }

            }
        }
        Card(
            modifier = Modifier.wrapContentSize()
                .shadow(10.dp, RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = if (tail) 0.dp else 10.dp,
                    bottomEnd = 10.dp),
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.LightGray
                ).padding(end = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = if (tail) 0.dp else 10.dp,
                bottomEnd = 10.dp
            ),
            border = BorderStroke(0.1.dp, Color.Unspecified)
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(max = 270.dp)
            ) {
                FlowRow(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = message.content,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left
                    )
                    Row(modifier = Modifier.align(Alignment.Bottom).offset(0.dp, (2).dp)) {
                        Space(2.dp)
                        Text(
                            text = message.timestamp,
                            fontSize = 10.sp,
                            color = Color.Gray,
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun MessageState(state: MessageState) {

    var messageStateResource = 0
    var size = 0
    var offsetX = 0
    var offsetY = 0
    var readFlag = false

    when (state) {

        MessageState.RECEIVED -> {
            messageStateResource = R.drawable.received_mark
            readFlag = false
            size = 13
            offsetX = -2
            offsetY = -2
        }

        MessageState.SENT -> {
            messageStateResource = R.drawable.sent_mark
            readFlag = false
            size = 10
            offsetX = -3
            offsetY = -1
        }

        MessageState.READ -> {
            messageStateResource = R.drawable.received_mark
            readFlag = true
            size = 13
            offsetX = -2
            offsetY = -2
        }

    }

    Icon(
        painter = painterResource(messageStateResource),
        tint = if (readFlag) Color.Blue else Color.Black,
        contentDescription = "mark",
        modifier = Modifier.padding(horizontal = if (state == MessageState.SENT) 1.dp else 0.dp)
            .size(size.dp).offset(offsetX.dp, offsetY.dp)
    )

}