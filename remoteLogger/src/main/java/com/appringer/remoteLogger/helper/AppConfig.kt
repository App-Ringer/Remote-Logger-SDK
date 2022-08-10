package com.appringer.remoteLogger.helper

import com.appringer.remoteLogger.enum.LogLevelEnum

object AppConfig {
    const val API_HEADER = "x-api-key"
    var API_KEY = ""
    var isLogCatEnable: Boolean = true
    var defaultTag: String = "Log"
    var networkStatus: String = ""
    var simCount: Int = 0
    var appBuildVersion: String = ""
    var securityPath: String = ""
    var defaultLevel: LogLevelEnum = LogLevelEnum.VERBOSE
}