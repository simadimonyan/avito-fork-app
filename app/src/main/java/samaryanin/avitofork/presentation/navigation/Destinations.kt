package samaryanin.avitofork.presentation.navigation

import kotlinx.serialization.Serializable


/**
* Стартовое окно при запуске приложения
*/
@Serializable object StartScreen


/**
 * Окно для входа по номеру или почте
 */
@Serializable object Login


/**
 * Окно для регистрации
 */
@Serializable object SignUp

/**
 * Окно для регистрации профиля
 */
@Serializable object CreateProfileScreen

/**
* Вложенный Composable с Bottom Navigation Bar
* ---------------------------------------------
* - перекрывается всеми компонентами из [StartScreen]
*/
@Serializable object NestedMenuScreen


/**
* Меню для поиска актуальных объявлений
*/
@Serializable object SearchScreen

