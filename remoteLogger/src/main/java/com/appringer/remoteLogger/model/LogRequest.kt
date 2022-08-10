package com.appringer.remoteLogger.model

import android.os.Build
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.util.GSONUtils
import org.json.JSONObject


open class LogRequest(
    val level: String = AppConfig.defaultLevel.value,
    val tag: String = AppConfig.defaultTag,
    val description: String,
    val json: JSONObject = JSONObject(),
    val logDT: Long = System.currentTimeMillis(),
    val device_info: DeviceInfo = DeviceInfo()
)

data class DeviceInfo(
    val osVersion: String = Build.VERSION.SDK_INT.toString(),
    val platform: String = "Android",
    val sdkVersion:String = "1.0.6",
    val manufacturer:String = android.os.Build.MANUFACTURER,
    val model:String = android.os.Build.MODEL,
    val networkStatus:String = AppConfig.networkStatus,
    val buildVersion:String = AppConfig.appBuildVersion,
    val securityPatch:String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { android.os.Build.VERSION.SECURITY_PATCH } else { null },
)

fun LogRequest.toLogRequestForUpload() =
    LogRequestForUpload(
        level,
        tag,
        description,
        GSONUtils.toMap(json),
        logDT,
        device_info
    )

open class LogRequestForUpload(
    val level: String = AppConfig.defaultLevel.value,
    val tag: String = AppConfig.defaultTag,
    val description: String,
    val json: Map<String, Any>,
    val logDT: Long = System.currentTimeMillis(),
    val device_info: DeviceInfo = DeviceInfo()
)
