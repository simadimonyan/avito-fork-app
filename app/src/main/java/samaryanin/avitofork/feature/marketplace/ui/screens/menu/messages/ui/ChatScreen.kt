package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.state.MessagesViewModel

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun ChatPreview() {
    ChatContent()
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun ChatScreen(viewModel: MessagesViewModel, chatId: String) {



    ChatContent()
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 */
@Composable
fun ChatContent() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}