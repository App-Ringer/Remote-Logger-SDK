package com.appringer.remote_logger.logger

import com.appringer.remote_logger.model.LogRequest

internal interface Logger {
    fun sendLog(logRequest: LogRequest)
}
