package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.data.MessagesViewModel

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun DirectMessagesPreview() {
    DirectMessagesContent()
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun DirectMessagesScreen(viewModel: MessagesViewModel, id: String) {
    DirectMessagesContent()
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 */
@Composable
fun DirectMessagesContent() {

}