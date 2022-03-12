package com.appringer.remote_logger

import android.content.Context
import com.appringer.remote_logger.enum.LogTagEnum
import com.appringer.remote_logger.helper.AppConfig
import com.appringer.remote_logger.helper.Log
import com.appringer.remote_logger.helper.LoggerHelper
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

    private fun reportUncaughtException(throwable: Throwable){
        throwable.cause?.stackTrace?.firstOrNull()?.let {
            LoggerHelper.log(LogTagEnum.ERROR.value,it).also { formattedLog->
                Log.send(LogRequest(
                    LogTagEnum.ERROR.value,
                    LogTagEnum.ERROR.value,
                    "Uncaught Exception",
                    GSONUtils.toString(formattedLog),
                ))
            }
        }
    }

    companion object {
        internal fun register(apiKey:String) {
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