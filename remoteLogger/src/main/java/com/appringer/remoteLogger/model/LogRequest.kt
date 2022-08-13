package com.appringer.remoteLogger.model

import android.os.Build
import com.appringer.remoteLogger.BuildConfig
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.util.GSONUtils
import org.json.JSONObject


open class LogRequest(
    val level: String = AppConfig.DEFAULT_LEVEL.value,
    val tag: String = AppConfig.DEFAULT_TAG,
    val description: String,
    val json: JSONObject = JSONObject(),
    val logDT: Long = System.currentTimeMillis(),
    val device_info: DeviceInfo = DeviceInfo()
)

data class DeviceInfo(
    val appBuildVersion: String = AppConfig.APP_BUILD_VERSION,
    val version: String = Build.VERSION.BASE_OS,
    val osVersion: Int = Build.VERSION.SDK_INT,
    val release: String = Build.VERSION.RELEASE,
    val previewSDK: Int = Build.VERSION.PREVIEW_SDK_INT,
    val sdkVersion: String = BuildConfig.sdkVersion,
    val manufacturer: String = Build.MANUFACTURER,

    val model: String = Build.MODEL,
    val device: String = Build.DEVICE,
    val brand: String = Build.BRAND,
    val display: String = Build.DISPLAY,
    val fingerprint: String = Build.FINGERPRINT,
    val bootloader: String = Build.BOOTLOADER,
    val product: String = Build.PRODUCT,
    val networkStatus: String = AppConfig.NETWORK_STATUS,

    val platform: String = "Android",
    val securityPatch: String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Build.VERSION.SECURITY_PATCH
    } else {
        null
    },
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
    val level: String = AppConfig.DEFAULT_LEVEL.value,
    val tag: String = AppConfig.DEFAULT_TAG,
    val description: String,
    val json: Map<String, Any>,
    val logDT: Long = System.currentTimeMillis(),
    val device_info: DeviceInfo = DeviceInfo()
)
