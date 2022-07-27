package com.appringer.remoteLogger.model

import android.os.Build
import com.appringer.remoteLogger.helper.AppConfig
import org.json.JSONObject

open class LogRequest(
    val level: String = AppConfig.defaultLevel.value,
    val tag: String = AppConfig.defaultTag,
    val description: String,
    val json: JSONObject = JSONObject(),
    val logDT: Long = System.currentTimeMillis(),
    val device_info:DeviceInfo = DeviceInfo()
)

data class DeviceInfo(
    val osVersion:String = Build.VERSION.SDK_INT.toString(),
    val platform:String = "Android"
)
