package com.appringer.remoteLogger.repo.logger

import android.content.Context
import com.appringer.remoteLogger.enum.LogLevelEnum

interface LoggerConfig {
    fun register(context: Context,apiKey: String, enableLogCat: Boolean = true, defaultTag: String = "")
    fun enableLogCat()
    fun disableLogCat()
    fun unregister()
    fun setTag(tag:String)
    fun setLevel(level:LogLevelEnum)
}