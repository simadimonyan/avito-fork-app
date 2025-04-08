package samaryanin.avitofork.data.repository.network

import androidx.compose.runtime.Stable
import retrofit2.HttpException
import retrofit2.Response
import samaryanin.avitofork.data.network.Result
import samaryanin.avitofork.data.network.RetrofitClient
import samaryanin.avitofork.data.network.dto.auth.account.AccountResponse
import samaryanin.avitofork.data.network.dto.auth.account.RefreshRequest
import samaryanin.avitofork.data.network.dto.auth.session.LoginRequest
import samaryanin.avitofork.data.network.dto.auth.session.RegisterRequest
import samaryanin.avitofork.data.network.dto.auth.session.SessionResponse
import samaryanin.avitofork.data.network.dto.auth.session.VerifyRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Stable
class AuthServiceRepository @Inject constructor() {

    private val client: RetrofitClient = RetrofitClient(
        "http://10.0.2.2:8080" // TODO(RuStore RemoteConfig)
    )

    private suspend fun <T> executeRequest(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(NullPointerException("Network issues!"))
            } else {
                Result.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun refresh(authToken: String, request: RefreshRequest): Result<AccountResponse> {
        return executeRequest { client.account.refresh("Bearer $authToken", request) }
    }

    suspend fun login(request: LoginRequest): Result<SessionResponse> {
        return executeRequest { client.session.login(request) }
    }

    suspend fun register(request: RegisterRequest): Result<SessionResponse> {
        return executeRequest { client.session.register(request) }
    }

    suspend fun verify(authToken: String, request: VerifyRequest): Result<SessionResponse> {
        return executeRequest { client.session.verify("Bearer $authToken", request) }
    }

}