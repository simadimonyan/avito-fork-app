package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.ui.data.MessagesViewModel

/**
 * Функция для предпросмотра макета
 */
@Preview
@Composable
fun MessagesPreview() {
    MessagesContent()
}

/**
 * State Hoisting паттерн
 * -------------------------------------
 * @param viewModel модель обработки состояний сообщений
 */
@Composable
fun MessagesScreen(viewModel: MessagesViewModel) {
    MessagesContent()
}

/**
 * Встраиваемый компонент окна
 * -------------------------------------
 */
@Composable
fun MessagesContent() {

}