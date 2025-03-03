package samaryanin.avitofork.data.network.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import samaryanin.avitofork.data.network.dto.auth.session.LoginRequest
import samaryanin.avitofork.data.network.dto.auth.session.RegisterRequest
import samaryanin.avitofork.data.network.dto.auth.session.SessionResponse
import samaryanin.avitofork.data.network.dto.auth.session.VerifyRequest

interface ISession {

    @Headers("Content-Type: application/json")
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<SessionResponse.LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<SessionResponse.RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("/verify")
    suspend fun verify(@Header("Authorization") authToken: String, @Body request: VerifyRequest): Response<SessionResponse.VerifyResponse>

}