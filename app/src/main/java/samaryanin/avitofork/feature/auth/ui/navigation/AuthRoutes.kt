package samaryanin.avitofork.feature.auth.ui.navigation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

/**
 * Ветка над BottomNavigation
 */
@Serializable
sealed class AuthRoutes(val route: String) {

    /**
     * Идентификатор вложенной навигации
     */
    @Serializable object RouteID : AuthRoutes("auth_id")

    /**
     * Окно для входа по номеру или почте
     */
    @Serializable object Login : AuthRoutes("login")

    /**
     * Окно для регистрации
     */
    @Serializable object SignUp : AuthRoutes("signup")

    /**
     * Окно для верификации кода
     * - /{createProfile} условие навигации окна создания профиля
     */
    @Serializable
    @Stable
    object Verification : AuthRoutes("verification/{createProfile}") {
        fun createRoute(createProfile: Boolean): String {
            return route.replace("{createProfile}", createProfile.toString())
        }
    }

    /**
     * Окно для создания профиля
     */
    @Serializable object CreateProfile : AuthRoutes("create_profile")

}