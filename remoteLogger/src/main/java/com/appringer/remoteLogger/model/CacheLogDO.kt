package com.appringer.remoteLogger.model

import com.appringer.remoteLogger.util.GSONUtils
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CacheLogDO(
    @PrimaryKey
    var id:Long=0L,
    var logRequest: String? = null,
    var isSynced: Boolean = false,
):RealmObject()
//{
//    fun getRequest() = logRequest?.let {
//        GSONUtils.getObj(logRequest,LogRequest::class.java)
//    }
//}