package samaryanin.avitofork.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import samaryanin.avitofork.presentation.screens.menu.profile.data.ProfileViewModel
import samaryanin.avitofork.presentation.screens.menu.profile.navigation.utilProfileGraph
import samaryanin.avitofork.presentation.screens.menu.profile.poster.data.CategoryViewModel
import samaryanin.avitofork.presentation.screens.menu.profile.poster.navigation.utilPosterGraph
import samaryanin.avitofork.presentation.screens.menu.search.navigation.SearchRoutes
import samaryanin.avitofork.presentation.screens.menu.search.navigation.utilSearchGraph
import samaryanin.avitofork.presentation.screens.start.data.MainViewModel

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
    profileViewModel: ProfileViewModel,
    categoriesViewModel: CategoryViewModel,
    globalNavController: NavHostController
) {
    navigation(startDestination = SearchRoutes.AdditionalInfoScreen.route, route = MainRoutes.UtilRouteID.route) {

        utilProfileGraph(globalNavController)
        utilSearchGraph(globalNavController)
        utilPosterGraph(globalNavController, profileViewModel, categoriesViewModel)

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
