package com.appringer.remote_logger.retrofit

import com.appringer.remote_logger.constant.URLConstant
import com.appringer.remote_logger.helper.AppConfig
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.model.LogResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoggerApi {

    @POST(URLConstant.LOG)
    suspend fun search(@Header(AppConfig.API_HEADER) apiKey: String = AppConfig.API_KEY,
                       @Body logRequest: LogRequest
    ): Response<LogResponse>

}