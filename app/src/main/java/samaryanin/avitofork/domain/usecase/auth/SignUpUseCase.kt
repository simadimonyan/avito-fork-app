//package samaryanin.avitofork.domain.usecase.auth
//
//import android.util.Log
//import androidx.compose.runtime.Immutable
//import samaryanin.avitofork.data.network.Result
//import samaryanin.avitofork.data.network.dto.auth.session.RegisterRequest
//import samaryanin.avitofork.data.network.dto.auth.session.SessionResponse
//import samaryanin.avitofork.domain.model.auth.AuthStatus
//import samaryanin.avitofork.domain.repository.NetworkRepository
//import samaryanin.avitofork.domain.state.DomainStateStore
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//@Immutable
//class SignUpUseCase @Inject constructor(
//    private val networkRepository: NetworkRepository,
//    private val state: DomainStateStore
//) {
//
//    private val authService = networkRepository.authServiceRepository
//
//    suspend fun signUp(email: String, password: String): AuthStatus {
//
//        try {
//            when (val result = authService.register(RegisterRequest(email, password))) {
//
//                is Result.Success -> {
//
//                    if (result.data is SessionResponse.LoginResponse) {
//                        state.authTokenStateHolder.updateServiceToken(result.data.token)
//                        return AuthStatus.SIGNUP_SUCCEED
//                    }
//                    else if (result.data is SessionResponse.SessionErrorResponse) {
//                        return if (result.data.message.contains("user already exists")) {
//                            AuthStatus.USER_ALREADY_EXISTS_ERROR
//                        } else {
//                            AuthStatus.ERROR(result.data.message)
//                        }
//                    }
//
//                }
//
//                is Result.Error -> {
//                    Log.w("SignUpUseCase", result.exception)
//                    return AuthStatus.NETWORK_ISSUES("${result.exception.message}")
//                }
//
//            }
//        }
//        catch (e: Exception) {
//            e.printStackTrace()
//            return AuthStatus.ERROR("${e.message}")
//        }
//
//        return AuthStatus.ERROR("Unknown error!")
//    }
//
//}