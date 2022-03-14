package com.appringer.remote_logger.logger

import com.appringer.remote_logger.enum.LogLevelEnum
import com.appringer.remote_logger.model.LogRequest
import java.lang.Exception

object RemoteLogger {
    private val logger = LoggerImpl

    fun sendLog(logRequest: LogRequest) {
        logger.sendLog(logRequest)
    }

    fun sendException(e: Exception) {
        logger.sendLog(e)
    }

    fun register(apiKey: String, enableLogCat: Boolean = true, defaultTag: String = "") {
        logger.register(apiKey)
    }

    fun unregister() {
        logger.unregister()
    }

    fun enableLogCat() {}
    fun disableLogCat() {}

    fun d(message: String, tag: String = "") {
        if (tag.isEmpty()) {
            //use default tag
        }
    }

    fun i(message: String, tag: String = "") {}
    fun v(message: String, tag: String = "") {}
    fun e(message: String, tag: String = "") {}
    fun log(message: String, tag: String = "XYZ", level: LogLevelEnum = LogLevelEnum.INFO) {}

    //level: CRASH
    fun log(e: Throwable) {}


}