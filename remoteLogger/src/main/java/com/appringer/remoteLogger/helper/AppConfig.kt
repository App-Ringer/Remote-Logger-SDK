package com.appringer.remoteLogger.helper

import com.appringer.remoteLogger.enum.LogLevelEnum

object AppConfig {
    const val API_HEADER = "x-api-key"
    var API_KEY = ""
    var isLogCatEnable: Boolean = true
    var defaultTag: String = "Log"
    var defaultLevel: LogLevelEnum = LogLevelEnum.VERBOSE
}