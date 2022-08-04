package com.appringer.remoteLogger.repo.network

import com.appringer.remoteLogger.constant.URLConstant
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.model.LogRequestForUpload
import com.appringer.remoteLogger.model.LogResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitApi {

    @POST(URLConstant.LOG)
    suspend fun log(@Header(AppConfig.API_HEADER) apiKey: String = AppConfig.API_KEY,
                    @Body logRequest: LogRequestForUpload
    ): Response<LogResponse>

}