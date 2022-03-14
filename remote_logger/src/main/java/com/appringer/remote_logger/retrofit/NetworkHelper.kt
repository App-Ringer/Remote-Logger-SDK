package com.appringer.remote_logger.retrofit

import com.appringer.remote_logger.logger.Logger
import com.appringer.remote_logger.helper.LoggerHelper
import com.appringer.remote_logger.model.LogRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

object NetworkHelper: Logger {

   override fun sendLog(logRequest: LogRequest){
       GlobalScope.launch {
            withContext(Dispatchers.IO){
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
    }

}