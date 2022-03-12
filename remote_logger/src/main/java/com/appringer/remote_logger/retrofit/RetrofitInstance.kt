package com.appringer.remote_logger.retrofit

import com.appringer.remote_logger.constant.URLConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    internal val api: LoggerApi by lazy<LoggerApi> {
        Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggerClient().build())
            .build()
            .create(LoggerApi::class.java)
    }

    internal fun getLoggerClient(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient
    }
}