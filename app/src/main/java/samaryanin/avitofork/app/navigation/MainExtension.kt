package samaryanin.avitofork.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import samaryanin.avitofork.app.activity.data.MainViewModel
import samaryanin.avitofork.feature.feed.ui.navigation.FeedRoutes
import samaryanin.avitofork.feature.feed.ui.navigation.utilSearchGraph
import samaryanin.avitofork.feature.messages.ui.navigation.utilMessagesGraph
import samaryanin.avitofork.feature.messages.ui.state.MessagesViewModel
import samaryanin.avitofork.feature.poster.ui.navigation.utilPosterGraph
import samaryanin.avitofork.feature.poster.ui.state.CategoryViewModel
import samaryanin.avitofork.feature.profile.ui.state.profile.ProfileViewModel
import samaryanin.avitofork.feature.profile.ui.navigation.profile.utilProfileGraph

/**
 * Расширение утилитной навигации Navigation Graph выше слоя BottomNavigation
 * ------------------------------------------------------------------------
 * @param mainViewModel главная модель приложения
 * @param profileViewModel модель профиля
 * @param categoriesViewModel модель категорий объявлений приложения
 * @param globalNavController глобальный контроллер навигации
 */
fun NavGraphBuilder.utilGraph(
    mainViewModel: MainViewModel,
    messagesViewModel: MessagesViewModel,
    profileViewModel: ProfileViewModel,
    categoriesViewModel: CategoryViewModel,
    globalNavController: NavHostController
) {
    navigation(startDestination = FeedRoutes.AdditionalInfoScreen.route, route = MainRoutes.UtilRouteID.route) {

        utilProfileGraph(globalNavController)
        utilSearchGraph(globalNavController)
        utilPosterGraph(globalNavController, profileViewModel, categoriesViewModel)
        utilMessagesGraph(messagesViewModel, globalNavController)

    }
}

//class TransitionAnimation {
//
//    companion object {
//
//        private val RootScreenStackAnimatorDuration = 400
//
//        fun rootScreenStackAnimator() = stackAnimator(
//            animationSpec = tween(durationMillis = RootScreenStackAnimatorDuration)
//        ) { factor, direction, content ->
//            content(
//                Modifier
//                    .layout { measurable, constraints ->
//                        val placeable = measurable.measure(constraints)
//                        val width = placeable.width
//
//                        layout(width, placeable.height) {
//                            placeable.placeRelative(
//                                x = if (direction.isBack) {
//                                    (width * 0.25f * factor).toInt()
//                                } else {
//                                    (width * factor).toInt()
//                                },
//                                y = 0
//                            )
//                        }
//                    }
//                    .drawWithContent {
//                        drawContent()
//                        drawRect(
//                            color = if (direction.isBack) {
//                                Color.Black.copy(alpha = (factor * 0.25f).absoluteValue)
//                            } else {
//                                Color.Transparent
//                            }
//                        )
//                    }
//            )
//        }
//
//    }
//
//}
