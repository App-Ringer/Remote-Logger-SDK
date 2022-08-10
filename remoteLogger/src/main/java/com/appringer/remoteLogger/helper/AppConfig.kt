package com.appringer.remoteLogger.helper

import com.appringer.remoteLogger.enum.LogLevelEnum

object AppConfig {
    const val API_HEADER = "x-api-key"
    var API_KEY = ""
    var IS_LOGCAT_ENABLE: Boolean = true
    var DEFAULT_TAG: String = "Log"
    var NETWORK_STATUS: String = ""
    var SIM_COUNT: Int = 0
    var APP_BUILD_VERSION: String = ""
    var DEFAULT_LEVEL: LogLevelEnum = LogLevelEnum.VERBOSE
}