package com.appringer.remoteLogger.repo.logger

import android.content.Context
import com.appringer.remoteLogger.enum.LogLevelEnum

interface LoggerConfig {
    fun register(
        context: Context,
        apiKey: String,
        enableLogCat: Boolean = true,
        defaultTag: String = "",
        appBuildVersion: String = ""
    )

    fun enableLogCat()
    fun disableLogCat()
    fun unregister()

    @Deprecated("Use setDefaultLevel instead.", ReplaceWith("this.setDefaultLevel(level)"))
    fun setLevel(level: LogLevelEnum)
    fun setDefaultLevel(level: LogLevelEnum)

    @Deprecated("Use setDefaultTag instead.", ReplaceWith("this.setDefaultTag(tag)"))
    fun setTag(tag: String)
    fun setDefaultTag(tag: String)
}