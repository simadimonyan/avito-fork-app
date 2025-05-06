package samaryanin.avitofork.feature.messages.ui.feature.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.messages.domain.models.Chat
import samaryanin.avitofork.feature.messages.domain.models.Message
import samaryanin.avitofork.feature.messages.domain.models.MessageState
import samaryanin.avitofork.feature.messages.ui.feature.messages.components.MessageItemList
import samaryanin.avitofork.feature.messages.ui.state.MessagesEvent
import samaryanin.avitofork.feature.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.shared.ui.components.utils.space.Divider
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.adaptive.LocaleDimensions
import samaryanin.avitofork.shared.ui.theme.adaptive.layout
import samaryanin.avitofork.shared.ui.theme.blendWhite
import samaryanin.avitofork.shared.ui.theme.greyButton
import samaryanin.avitofork.shared.ui.theme.lightBlue
import samaryanin.avitofork.shared.ui.theme.saladGreen

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun ChatPreview() {
    ChatContent(
        Chat(
            "",
            "Иван В. Д.",
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
                    mutableListOf(),
                    "150 000",
                    "руб."
                ),
                "22.04.25"
            )
        ),
        {}
    ) {}
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun ChatScreen(globalNavController: NavHostController, viewModel: MessagesViewModel, directId: String) {

    val state by viewModel.appStateStore.messageStateHolder.messagesState.collectAsState()
    val chat = state.chats.find { it.chatId == directId } ?: return

    val keyboardController = LocalSoftwareKeyboardController.current

    // навигация в личные сообщения по чату
    val navigateUp: () -> Unit = {
        keyboardController?.hide()
        globalNavController.navigateUp()
    }

    val sendMessage: (String) -> Unit = { message ->
        viewModel.handleEvent(MessagesEvent.SendMessage(chat.chatId, Message(content = message)))
    }

    ChatContent(chat, navigateUp, sendMessage)
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ChatContent(chat: Chat, navigateUp: () -> Unit, sendMessage: (String) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        CompositionLocalProvider(LocaleDimensions provides layout.getThemeSize(maxWidth)) {

            val LocalDimensions = LocaleDimensions.current

            Scaffold(
                containerColor = Color.White,
                topBar = {

                    Card(
                        modifier = Modifier.fillMaxWidth().zIndex(1f),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(2.dp)
                    ) {

                        Column(verticalArrangement = Arrangement.Center) {

                            Row(modifier = Modifier
                                .fillMaxWidth().background(Color.White).zIndex(2f)
                                .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow),
                                    modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeTopBar2).clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        navigateUp()
                                    },
                                    contentDescription = "back"
                                )

                                Space(6.dp)

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(LocalDimensions.Chat.IconSize.iconSizeTopBar4)
                                        .background(greyButton),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = chat.profileName.firstOrNull()?.toString() ?: "",
                                        fontSize = LocalDimensions.Chat.FontSize.fontSizeTitle3,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }

                                Space()

                                Column(modifier = Modifier.padding(end = 15.dp), verticalArrangement = Arrangement.Center) {

                                    Row {

                                        Text(
                                            text = chat.profileName.take(15),
                                            fontSize = LocalDimensions.Chat.FontSize.fontSizeTitle2,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left,
                                            maxLines = 1,
                                            modifier = Modifier.wrapContentWidth()
                                                .drawWithContent {
                                                    if (chat.profileName.length >= 12) {
                                                        val fadeWidth = size.width * 0.5f
                                                        drawContent()
                                                        drawRect(
                                                            brush = Brush.horizontalGradient(
                                                                colors = listOf(Color.Transparent, Color.White),
                                                                startX = size.width - fadeWidth,
                                                                endX = size.width,
                                                                tileMode = TileMode.Clamp
                                                            ),
                                                            size = size
                                                        )
                                                    } else {
                                                        drawContent()
                                                    }
                                                }
                                        )

                                        Space(1.dp)

                                        Icon(
                                            Icons.Default.NotificationsActive,
                                            modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeTopBar1),
                                            contentDescription = "notification",
                                            tint = Color.LightGray
                                        )
                                    }

                                    Text(
                                        text = "в сети",
                                        fontSize = LocalDimensions.Chat.FontSize.fontSizeTitle1,
                                        fontWeight = FontWeight.Normal,
                                        color = saladGreen
                                    )

                                }

                                Spacer(Modifier.weight(1f))

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Icon(
                                        painter = painterResource(R.drawable.phone_call),
                                        modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeTopBar2).clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            // TODO (сделать звонок)
                                        },
                                        contentDescription = "phone_call",
                                        tint = lightBlue
                                    )

                                    Space()

                                    Icon(
                                        painter = painterResource(R.drawable.video_call),
                                        modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeTopBar3).clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            // TODO (сделать видеозвонок)
                                        },
                                        contentDescription = "video_call",
                                        tint = lightBlue
                                    )

                                    Space(5.dp)
                                }

                            }

                            if (chat.postReference.data.name.isNotEmpty()) {

                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                                    Space()

                                    Box(
                                        modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeTopBar4).background(greyButton,
                                            RoundedCornerShape(5.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = chat.postReference.data.name.firstOrNull()?.toString() ?: "",
                                            fontSize = LocalDimensions.Chat.FontSize.fontSizeAttachmentPost,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }

                                    Space()

                                    Column(modifier = Modifier.padding(vertical = 1.dp).align(Alignment.CenterVertically)) {

                                        val name = if (chat.postReference.data.name.length > 15) {
                                            "${chat.postReference.data.name.take(15)}..."
                                        } else {
                                            chat.postReference.data.name
                                        }

                                        Text(
                                            text = name,
                                            fontSize = LocalDimensions.Chat.FontSize.fontSizeAttachmentPost,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Black,
                                            textAlign = TextAlign.Left
                                        )

                                        Text(
                                            text = "от ${chat.postReference.timestamp}",
                                            fontSize = LocalDimensions.Chat.FontSize.fontSizeAttachmentPost,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Gray
                                        )

                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = chat.postReference.data.price + " " + chat.postReference.data.unit,
                                        fontSize = LocalDimensions.Chat.FontSize.fontSizeAttachmentPrice,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )

                                    Space()

                                }

                                Space()

                            }
                            else
                                Space(1.dp)

                        }

                    }

                },
                content = { innerPadding ->

                    Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()).consumeWindowInsets(innerPadding).fillMaxSize()) {

                        Column (modifier = Modifier
                            .background(blendWhite)
                            .fillMaxSize()
                            .padding(horizontal = 5.dp)
                        ) {
                            MessageItemList("myId", chat.messages, Modifier.padding(top = innerPadding.calculateTopPadding()))
                        }

                    }

                },
                bottomBar = {

                    Divider()

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .imePadding().consumeWindowInsets(WindowInsets(0))
                        .padding(vertical = 15.dp, horizontal = 17.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        var flagMessageReady by remember { mutableStateOf(false) }
                        var message by remember { mutableStateOf("") }
                        var placeholder by remember { mutableStateOf("") }

                        Icon(
                            painter = painterResource(R.drawable.attach_file),
                            modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeBottomBar1).clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                // TODO (добавить файл)
                            },
                            contentDescription = "attach_file",
                            tint = Color.LightGray
                        )

                        Space()

                        Box(modifier = Modifier.weight(1f)) {
                            MessageTextField(message, "Сообщение...") { it ->
                                message = it
                                placeholder = if (it.isEmpty()) placeholder else ""
                                flagMessageReady = !it.isEmpty()
                            }
                        }

                        Space()

                        Icon(
                            painter = painterResource(R.drawable.send_message),
                            modifier = Modifier.size(LocalDimensions.Chat.IconSize.iconSizeBottomBar1).clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (flagMessageReady) {
                                    if (message.isNotBlank()) sendMessage(message)
                                    message = ""
                                    flagMessageReady = false
                                }
                            },
                            contentDescription = "send_message",
                            tint = if (flagMessageReady) lightBlue else Color.LightGray
                        )

                    }

                }
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTextField(value: String, placeholder: String, onValueChanged: (String) -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .background(Color.White)
            .heightIn(max = 100.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            BasicTextField(
                value = value,
                onValueChange = {

                    onValueChanged(it)
                },
                modifier = Modifier.weight(1f),
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = true,
                        placeholder = @Composable {
                            Text(text = placeholder, fontSize = 15.sp, color = Color.Gray)
                        },
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(0.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                        )
                    )
                }
            )
        }

    }
}