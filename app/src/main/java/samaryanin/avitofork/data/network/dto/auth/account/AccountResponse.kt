package samaryanin.avitofork.data.network.dto.auth.account

import com.google.gson.annotations.SerializedName

sealed class AccountResponse {

    /**
     * /refresh - в случае неуспешного запроса
     */
    data class AccountErrorResponse(
        val code: String,
        val message: String
    ) : AccountResponse()

    /**
     * /refresh - в случае успешного запроса
     */
    data class RefreshResponse(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("refresh_token") val refreshToken: String
    ) : AccountResponse()

}

