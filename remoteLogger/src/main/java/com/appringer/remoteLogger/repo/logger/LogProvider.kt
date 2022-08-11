package com.appringer.remoteLogger.repo.logger

import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.helper.AppConfig
import org.json.JSONObject

internal interface LogProvider {
    fun sendLog(
        message: JSONObject?,
        tag: String? = null,
        desc: String? = null,
        logLevelEnum: LogLevelEnum? = AppConfig.DEFAULT_LEVEL
    )

    fun sendLog(
        t: Throwable,
        tag: String? = null,
        desc: String? = null,
        logLevelEnum: LogLevelEnum? = AppConfig.DEFAULT_LEVEL
    )
}
