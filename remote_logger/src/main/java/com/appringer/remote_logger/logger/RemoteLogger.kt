package com.appringer.remote_logger.logger

import com.appringer.remote_logger.model.LogRequest
import java.lang.Exception

object RemoteLogger {
    private val logger = LoggerImpl

    fun sendLog(logRequest: LogRequest) {
        logger.sendLog(logRequest)
    }

    fun sendException(e:Exception){
        logger.sendLog(e)
    }

    fun register(apiKey:String){
        logger.register(apiKey)
    }

    fun unregister(){
        logger.unregister()
    }
}