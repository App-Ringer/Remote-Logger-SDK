package com.appringer.remote_logger.logger

import com.appringer.remote_logger.AppRingerExceptionHandler
import com.appringer.remote_logger.enum.LogTagEnum
import com.appringer.remote_logger.helper.LoggerHelper
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.retrofit.NetworkHelper
import com.appringer.remote_logger.util.GSONUtils

object LoggerImpl:Logger {

    override fun sendLog(logRequest: LogRequest) {
        NetworkHelper.sendLog(logRequest)
    }

    fun register(apiKey:String){
        AppRingerExceptionHandler.register(apiKey)
    }

    fun unregister(){
        AppRingerExceptionHandler.unregister()
    }

    fun sendLog(e:Exception) {
        val log = GSONUtils.toString(e.cause?.cause?.stackTrace)
        LoggerHelper.log(LogTagEnum.ERROR.value, log)
        sendLog(
            LogRequest(
                LogTagEnum.ERROR.value,
                LogTagEnum.ERROR.value,
                "Exception",
                log,
            )
        )
    }

}