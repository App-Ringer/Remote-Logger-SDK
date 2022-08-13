package com.appringer.remoteLogger.repo.network

import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.helper.LoggerHelper
import com.appringer.remoteLogger.model.CacheLogDO
import com.appringer.remoteLogger.model.LogRequest
import com.appringer.remoteLogger.model.toLogRequestForUpload
import com.appringer.remoteLogger.repo.logger.LogProvider
import com.appringer.remoteLogger.repo.storage.StorageRepoImp
import com.appringer.remoteLogger.util.GSONUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

object NetworkHelper : LogProvider {

    private fun sendLog(
        id: Long = System.currentTimeMillis(),
        logReq: String,
        isSynced: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cacheLogInThread = CacheLogDO(id, logReq, isSynced)
                GSONUtils.getObj(logReq, LogRequest::class.java).let { logRequest ->
                    StorageRepoImp.saveCallLog(cacheLogInThread)
                    val response =
                        RetrofitInstance.api.log(logRequest = logRequest.toLogRequestForUpload())
                    if (response.body()?.isSuccess() == true) {
                        LoggerHelper.log("RemoteLog Submitted ${response.body()?.message}")
                        cacheLogInThread.isSynced = true
                        StorageRepoImp.updateUploadSuccess(cacheLogInThread)
                    } else {
                        LoggerHelper.log("RemoteLog Failed ${response.body()?.message}")
                    }
                }
            } catch (e: Exception) {
                LoggerHelper.log(e)
            }
        }
    }

    override fun sendLog(
        message: JSONObject?,
        tag: String?,
        desc: String?,
        logLevelEnum: LogLevelEnum?
    ) {
        val logRequest = getLogRequest(
            message,
            logLevelEnum ?: AppConfig.DEFAULT_LEVEL,
            tag,
            desc
        )
        sendLog(
            System.currentTimeMillis(),
            GSONUtils.toString(logRequest),
            false
        )
    }

    override fun sendLog(t: Throwable, tag: String?, desc: String?, logLevelEnum: LogLevelEnum?) {
        val json = JSONObject()
        json.put("stackTrace", t.stackTrace)

        var description: String? = null
        t.stackTrace.firstOrNull()?.apply {
            json.put("lineNumber", lineNumber)
            json.put("fileName", fileName)
            json.put("declaringClass", className)

            description = "Crash at lineNumber: $lineNumber in file: $fileName"
        }

        val logRequest = getLogRequest(
            json,
            logLevelEnum ?: AppConfig.DEFAULT_LEVEL,
            tag,
            desc ?: description
        )
        sendLog(
            System.currentTimeMillis(),
            GSONUtils.toString(logRequest),
            false
        )
    }

    fun pushUnSyncedLogs(cacheLogs: List<CacheLogDO>) {
        cacheLogs.forEach {
            sendLog(it.id, it.logRequest ?: "", it.isSynced)
        }
    }

    private fun getLogRequest(
        obj: JSONObject?,
        logLevelEnum: LogLevelEnum,
        tag: String? = null,
        desc: String? = null
    ): LogRequest {
        return LogRequest(
            logLevelEnum.value,
            tag ?: AppConfig.DEFAULT_TAG,
            desc ?: "",
            obj ?: JSONObject()
        )
    }

}