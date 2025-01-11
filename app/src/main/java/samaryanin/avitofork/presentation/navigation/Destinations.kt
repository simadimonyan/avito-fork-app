package samaryanin.avitofork.presentation.navigation

import kotlinx.serialization.Serializable

/*
* Старотовое окно при запуске приложения
*/
@Serializable object StartScreen


/*
* Вложенный Composable с Bottom Navigation Bar
* ---------------------------------------------
* - перекрывается всеми компонентами из StartScreen
*/
@Serializable object NestedMenuScreen



/*
* Меню для поиска актуальных объявлений
*/
@Serializable object SearchScreen