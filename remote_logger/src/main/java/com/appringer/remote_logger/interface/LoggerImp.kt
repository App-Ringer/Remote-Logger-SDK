package com.appringer.remote_logger.interface

import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.retrofit.NetworkHelper

class LoggerImp:Logger {
    override suspend fun sendLog(logRequest: LogRequest) {
        NetworkHelper.sendLog(logRequest)
    }
}