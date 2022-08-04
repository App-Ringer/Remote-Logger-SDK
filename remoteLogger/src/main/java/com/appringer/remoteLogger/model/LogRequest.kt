package com.appringer.remoteLogger.model

import android.os.Build
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.util.GSONUtils
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    val platform: String = "Android"
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
