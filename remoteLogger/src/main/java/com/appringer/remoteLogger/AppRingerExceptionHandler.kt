package com.appringer.remoteLogger

import android.content.Context
import com.appringer.remoteLogger.repo.logger.RemoteLogger

class AppRingerExceptionHandler internal constructor(
    var existingHandler: Thread.UncaughtExceptionHandler?,
    val context: Context
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        object : Thread() {
            override fun run() {
                reportUncaughtException(throwable, context)
            }
        }.start()
        if (existingHandler != null) {
            existingHandler!!.uncaughtException(thread, throwable)
        }
    }

    private fun reportUncaughtException(throwable: Throwable, context: Context) {
        RemoteLogger.crash(throwable, tag = context.packageName)
    }

    companion object {
        internal fun register(context: Context) {
            val existingHandler = Thread.getDefaultUncaughtExceptionHandler()
            if (existingHandler !is AppRingerExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(
                    AppRingerExceptionHandler(
                        existingHandler,
                        context
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