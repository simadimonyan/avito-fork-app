package samaryanin.avitofork.data.network.repository

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

    suspend fun refresh(authToken: String, request: RefreshRequest): Result<AccountResponse.RefreshResponse> {
        return executeRequest { client.account.refresh("Bearer $authToken", request) }
    }

    suspend fun login(request: LoginRequest): Result<SessionResponse.LoginResponse> {
        return executeRequest { client.session.login(request) }
    }

    suspend fun register(request: RegisterRequest): Result<SessionResponse.RegisterResponse> {
        return executeRequest { client.session.register(request) }
    }

    suspend fun verify(authToken: String, request: VerifyRequest): Result<SessionResponse.VerifyResponse> {
        return executeRequest { client.session.verify("Bearer $authToken", request) }
    }

}