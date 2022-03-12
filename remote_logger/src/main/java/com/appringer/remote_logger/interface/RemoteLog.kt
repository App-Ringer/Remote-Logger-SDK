package com.appringer.remote_logger.`interface`

import com.appringer.remote_logger.AppRingerExceptionHandler
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.retrofit.NetworkHelper

object RemoteLog:LogInterface {

    override suspend fun sendLog(logRequest: LogRequest) {
        NetworkHelper.sendLog(logRequest)
    }

    fun register(apiKey:String){
        AppRingerExceptionHandler.register(apiKey)
    }

    fun unregister(){
        AppRingerExceptionHandler.unregister()
    }

}