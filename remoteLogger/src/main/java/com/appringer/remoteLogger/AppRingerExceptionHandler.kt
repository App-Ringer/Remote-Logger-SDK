package com.appringer.remoteLogger

import com.appringer.remoteLogger.repo.logger.RemoteLogger

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
        RemoteLogger.log(throwable)
    }

    companion object {
        internal fun register() {
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