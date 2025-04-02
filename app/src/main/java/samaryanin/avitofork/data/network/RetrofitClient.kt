package samaryanin.avitofork.data.network

import androidx.compose.runtime.Immutable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import samaryanin.avitofork.data.network.api.auth.IAccount
import samaryanin.avitofork.data.network.api.auth.ISession

@Immutable
sealed class Result<out T> {

    @Immutable
    data class Success<out T>(val data: T) : Result<T>()

    @Immutable
    data class Error(val exception: Throwable) : Result<Nothing>()

}

@Immutable
class RetrofitClient(private val urlString: String) {

    // -- debug mode --

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // ----------------

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(urlString)
            .client(client) // --debug only
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val account: IAccount by lazy {
        retrofit.create(IAccount::class.java)
    }

    val session: ISession by lazy {
        retrofit.create(ISession::class.java)
    }

}


