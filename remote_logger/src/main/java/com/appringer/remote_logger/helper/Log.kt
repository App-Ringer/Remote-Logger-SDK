package com.appringer.remote_logger.helper

import com.appringer.remote_logger.`interface`.RemoteLog
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.retrofit.LoggerApi
import com.appringer.remote_logger.retrofit.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Log {
    internal fun send(logRequest: LogRequest){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                RemoteLog.sendLog(logRequest)
            }
        }
    }
}