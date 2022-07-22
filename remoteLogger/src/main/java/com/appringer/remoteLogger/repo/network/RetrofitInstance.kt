package com.appringer.remoteLogger.repo.network

import com.appringer.remoteLogger.constant.URLConstant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val gson: Gson = GsonBuilder().setLenient().create()

    internal val api: RetrofitApi by lazy<RetrofitApi> {
        Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getLoggerClient().build())
            .build()
            .create(RetrofitApi::class.java)
    }

    private fun getLoggerClient(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient
    }
}