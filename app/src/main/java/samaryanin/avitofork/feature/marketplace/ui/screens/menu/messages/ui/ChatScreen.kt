package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.core.ui.utils.components.utils.space.Divider
import samaryanin.avitofork.core.ui.utils.components.utils.space.Space
import samaryanin.avitofork.core.ui.utils.theme.greyButton
import samaryanin.avitofork.core.ui.utils.theme.saladGreen
import samaryanin.avitofork.core.ui.utils.theme.veryLightBlueColor
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostData
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Chat
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.MessageState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components.MessageItemList
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel

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
        {}
    )
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun ChatScreen(globalNavController: NavHostController, viewModel: MessagesViewModel, chat: Chat) {

    // навигация в личные сообщения по чату
    val navigateUp: () -> Unit = {
        globalNavController.navigateUp()
    }

    ChatContent(chat, navigateUp)
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 */
@Composable
fun ChatContent(chat: Chat, navigateUp: () -> Unit) {
    Scaffold(
        containerColor = Color.White,
        topBar = {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    painter = painterResource(R.drawable.ic_arrow),
                    modifier = Modifier.size(30.dp).clickable {
                        navigateUp()
                    },
                    contentDescription = "back"
                )

                Space(7.dp)

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .background(greyButton),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${chat.profileName.toCharArray()[0]}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Space()

                Column(modifier = Modifier.padding(vertical = 2.dp)) {

                    Text(
                        text = chat.profileName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Left
                    )

                    Space(1.dp)

                    Text(
                        text = "В сети",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = saladGreen
                    )

                }

            }

        },

        bottomBar = {

            Divider()

            Row(modifier = Modifier
                    .fillMaxWidth()
                    .imePadding().consumeWindowInsets(WindowInsets(0))
                    .padding(vertical = 15.dp, horizontal = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.attach_file),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "back",
                    tint = Color.LightGray
                )

                Space()

                MessageTextField("", "Сообщение...") {}

                Space(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(R.drawable.send_message),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "back",
                    tint = Color.LightGray
                )

            }

        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            Divider()

            Column (modifier = Modifier
                .background(veryLightBlueColor)
                .wrapContentSize().fillMaxWidth()
                .padding(horizontal = 5.dp)
            ) {

                Space(modifier = Modifier.weight(1f))

                MessageItemList("", chat.messages as MutableList<Message>)

                Space(1.dp)
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTextField(value: String, placeholder: String, onValueChanged: (String) -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }
    var mutableValue by remember { mutableStateOf(value) }
    var mutablePlaceholder by remember { mutableStateOf(placeholder) }

    Box(
        modifier = Modifier
            .background(Color.White)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            BasicTextField(
                value = mutableValue,
                onValueChange = {
                    mutableValue = it
                    mutablePlaceholder = ""
                    onValueChanged(it)
                },
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = true,
                        placeholder = @Composable {
                            Text(text = mutablePlaceholder, fontSize = 15.sp, color = Color.Gray)
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