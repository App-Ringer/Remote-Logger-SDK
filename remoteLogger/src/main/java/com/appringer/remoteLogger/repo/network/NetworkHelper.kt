package com.appringer.remoteLogger.repo.network

import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.helper.LoggerHelper
import com.appringer.remoteLogger.model.CacheLogDO
import com.appringer.remoteLogger.model.LogRequest
import com.appringer.remoteLogger.model.LogStackTrace
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
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val cacheLogInThread = CacheLogDO(id, logReq, isSynced)
                GSONUtils.getObj(logReq, LogRequest::class.java).let { logRequest ->
                    StorageRepoImp.saveCallLog(cacheLogInThread)
                    val response =
                        RetrofitInstance.api.log(logRequest = logRequest)
                    if (response.body()?.code ?: 0 == 200) {
                        LoggerHelper.log("RemoteLog Submitted ${response.body()?.message}")
                        cacheLogInThread.isSynced = true
                        StorageRepoImp.updateUploadSuccess(cacheLogInThread)
                    } else {
                        LoggerHelper.log("RemoteLog Failed ${response.body()?.message}")
                    }
                }
            }
        } catch (e: Exception) {
            LoggerHelper.log(e)
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
            logLevelEnum?:AppConfig.defaultLevel,
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
        val logRequest = getLogRequest(
            GSONUtils.toJsonObject(LogStackTrace(t.stackTrace ?: arrayOf())),
            logLevelEnum?:AppConfig.defaultLevel,
            tag,
            desc
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
            tag ?: AppConfig.defaultTag,
            desc ?: "",
            obj?:JSONObject()
        )
    }

}