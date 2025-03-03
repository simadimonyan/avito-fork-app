package samaryanin.avitofork.data.network.dto.auth.account

import com.google.gson.annotations.SerializedName

/**
 * /refresh - формат тела запроса
 */
data class RefreshRequest(
    @SerializedName("refresh_token") val refreshToken: String
)