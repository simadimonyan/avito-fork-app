package samaryanin.avitofork.data.network.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import samaryanin.avitofork.data.network.dto.auth.account.AccountResponse
import samaryanin.avitofork.data.network.dto.auth.account.RefreshRequest

interface IAccount {

    @Headers("Content-Type: application/json")
    @GET("/access")
    suspend fun access(@Header("Authorization") authToken: String): Response<AccountResponse.AccessResponse>

    @Headers("Content-Type: application/json")
    @POST("/refresh")
    suspend fun refresh(@Header("Authorization") authToken: String, @Body request: RefreshRequest): Response<AccountResponse.RefreshResponse>

}