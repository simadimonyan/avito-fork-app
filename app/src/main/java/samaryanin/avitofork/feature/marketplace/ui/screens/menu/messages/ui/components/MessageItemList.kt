package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.MessageState

@Preview
@Composable
fun MessageItemPreview() {
    MessageItemList(mutableListOf(
      Message(
          "",
          "",
          "",
          "",
          MessageState.SENT
      )
    ))
}

@Composable
fun MessageItemList(messages: MutableList<Message>) {
    LazyColumn {

    }
}

/**
 * @param userId id авторизованного профиля
 * @param message сообщение
 */
@Composable
fun MessageItem(userId: String, message: Message) {
    if (userId == message.user) {

    }
}