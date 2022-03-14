package com.appringer.remote_logger

import com.appringer.remote_logger.enum.LogLevelEnum
import com.appringer.remote_logger.helper.AppConfig
import com.appringer.remote_logger.helper.LoggerHelper
import com.appringer.remote_logger.logger.RemoteLogger
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.util.GSONUtils

class AppRingerExceptionHandler internal constructor(
    var existingHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        object : Thread() {
            override fun run() {
                reportUncaughtException(throwable)
            }
        }.start()
        if (existingHandler != null) {
            existingHandler!!.uncaughtException(thread, throwable)
        }
    }

    private fun reportUncaughtException(throwable: Throwable) {
        val log = GSONUtils.toString(throwable.cause?.cause?.stackTrace)
        LoggerHelper.log(LogLevelEnum.ERROR.value, log)
        RemoteLogger.sendLog(
            LogRequest(
                LogLevelEnum.UNCAUGHT.value,
                LogLevelEnum.UNCAUGHT.value,
                "Uncaught Exception",
                log,
            )
        )
    }

    companion object {
        internal fun register(apiKey: String) {
            AppConfig.API_KEY = apiKey
            val existingHandler = Thread.getDefaultUncaughtExceptionHandler()
            if (existingHandler !is AppRingerExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(
                    AppRingerExceptionHandler(
                        existingHandler
                    )
                )
            }
        }

        internal fun unregister() {
            val currentHandler = Thread.getDefaultUncaughtExceptionHandler()
            if (currentHandler is AppRingerExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(currentHandler.existingHandler)
            }
        }
    }
}