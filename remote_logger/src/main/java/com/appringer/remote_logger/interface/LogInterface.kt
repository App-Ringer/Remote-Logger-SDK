package com.appringer.remote_logger.`interface`

import com.appringer.remote_logger.model.LogRequest

internal interface LogInterface {
    suspend fun sendLog(logRequest: LogRequest)
}
