package com.appringer.remoteLogger.repo.storage

import com.appringer.remoteLogger.enum.CacheLogStatus
import com.appringer.remoteLogger.model.CacheLogDO
import com.appringer.remoteLogger.repo.logger.RemoteLogger
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

object StorageRepoImp : StorageRepo {

    private var entries: RealmResults<CacheLogDO>? = null

    override suspend fun saveCallLog(log: CacheLogDO) {
        Realm.getDefaultInstance().executeTransactionAwait(Dispatchers.IO) {
            try {
                it.insert(log)
            } catch (e: Exception) {
                RemoteLogger.e(t = e)
            }
        }
    }

    override fun updateUploadSuccess(logs: CacheLogDO) {
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(logs)
        }
    }

    override fun getCallLogs(
        cacheLogStatus: CacheLogStatus,
        listener: RealmChangeListener<List<CacheLogDO>>
    ) {
        RemoteLogger.d("RealmChangeListener:Start")
        val realmQuery = Realm.getDefaultInstance().where(CacheLogDO::class.java)

        when (cacheLogStatus) {
            CacheLogStatus.SYNCED -> realmQuery.equalTo("isSynced", true)
            CacheLogStatus.NON_SYNCED -> realmQuery.equalTo("isSynced", false)
            else -> {}
        }

        entries = realmQuery.findAll()

        listener.onChange(entries ?: listOf())
    }
}