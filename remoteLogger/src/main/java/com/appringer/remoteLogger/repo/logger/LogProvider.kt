package com.appringer.remoteLogger.repo.logger

import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.model.CacheLogDO
import org.json.JSONObject

internal interface LogProvider {
    fun sendLog(
        message: JSONObject?,
        tag: String? = null,
        desc: String? = null,
        logLevelEnum: LogLevelEnum? = AppConfig.defaultLevel
    )

    fun sendLog(t: Throwable,
                tag: String? = null,
                desc: String? = null,
                logLevelEnum: LogLevelEnum? = AppConfig.defaultLevel)
}
