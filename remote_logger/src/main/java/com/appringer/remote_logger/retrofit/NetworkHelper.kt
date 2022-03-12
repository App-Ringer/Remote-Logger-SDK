package com.appringer.remote_logger.retrofit

import com.appringer.remote_logger.`interface`.LogInterface
import com.appringer.remote_logger.helper.LoggerHelper
import com.appringer.remote_logger.model.LogRequest
import java.lang.Exception

object NetworkHelper: LogInterface {

   override suspend fun sendLog(logRequest: LogRequest){
        try {
            val response = RetrofitInstance.api.search(logRequest = logRequest)
            if(response.body()?.code?:0 == 200){
                LoggerHelper.log("RemoteLog Submitted ${response.body()?.message}")
            }else{
                LoggerHelper.log("RemoteLog Failed ${response.body()?.message}")
            }
        }catch (e:Exception){
            LoggerHelper.log(e)
        }
    }

}