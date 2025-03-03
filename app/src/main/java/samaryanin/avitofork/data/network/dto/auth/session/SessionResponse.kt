package samaryanin.avitofork.data.network.dto.auth.session

import com.google.gson.annotations.SerializedName

sealed class SessionResponse {

    /**
     * /login, /register, /verify - в случае неуспешного запроса
     */
    data class SessionErrorResponse(
        val code: String,
        val message: String
    ) : SessionResponse()

    /**
     * /login - в случае успешного запроса
     */
    data class LoginResponse(
        val token: String
    ) : SessionResponse()

    /**
     * /register - в случае успешного запроса
     */
    data class RegisterResponse(
        val token: String
    ) : SessionResponse()

    /**
     * /verify - в случае успешного запроса
     */
    data class VerifyResponse(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("refresh_token") val refreshToken: String
    ) : SessionResponse()

}

