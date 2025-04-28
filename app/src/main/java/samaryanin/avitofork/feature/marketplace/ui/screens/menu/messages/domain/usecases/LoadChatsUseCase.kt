package samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.usecases

import androidx.compose.runtime.Immutable
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostData
import samaryanin.avitofork.feature.marketplace.domain.model.post.PostState
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Chat
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.Message
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.messages.domain.models.MessageState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class LoadChatsUseCase @Inject constructor() {

    fun loadChats(): MutableList<Chat> {
        return mutableListOf(
            Chat(
                "1",
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
                "2",
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
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "3",
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
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "4",
                "Евгений",
                mutableListOf(),
                PostState(
                    "",
                    "",
                    PostData(
                        "Macbook 14 pro M1 1T 32GB ",
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "5",
                "Поддержка AvitoFork",
                mutableListOf(
                    Message(
                        "",
                        "anotherId",
                        "Рады вам помочь!",
                        "1745406425571",
                        MessageState.READ
                    )
                ),
            ),
            Chat(
                "6",
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
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "7",
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
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "8",
                "Евгений",
                mutableListOf(),
                PostState(
                    "",
                    "",
                    PostData(
                        "Macbook 14 pro M1 1T 32GB ",
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "9",
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
                "10",
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
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "11",
                "Алексей",
                mutableListOf(
                    Message(
                        "",
                        "myId",
                        "Послезавтра готов забрать",
                        "17454064029923",
                        MessageState.SENT
                    )
                ),
                PostState(
                    "",
                    "",
                    PostData(
                        "Мангал для дачи",
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            ),
            Chat(
                "12",
                "Евгений",
                mutableListOf(),
                PostState(
                    "",
                    "",
                    PostData(
                        "Macbook 14 pro M1 1T 32GB ",
                        mutableListOf(),
                        "150 000",
                        "руб."
                    )
                )
            )
        )
    }

}