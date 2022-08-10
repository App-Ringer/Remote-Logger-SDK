package com.appringer.remoteLogger.repo.storage

import com.appringer.remoteLogger.enum.CacheLogStatus
import com.appringer.remoteLogger.model.CacheLogDO
import io.realm.RealmChangeListener

interface StorageRepo {
    suspend fun saveCallLog(log: CacheLogDO)

    fun updateUploadSuccess(logs: CacheLogDO)

    fun getCallLogs(cacheLogStatus: CacheLogStatus = CacheLogStatus.ALL,listener: RealmChangeListener<List<CacheLogDO>>)
}