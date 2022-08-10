package com.appringer.remoteLogger.repo.logger

import android.content.Context
import android.os.Build
import com.appringer.remoteLogger.AppRingerExceptionHandler
import com.appringer.remoteLogger.enum.CacheLogStatus
import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.helper.LoggerHelper
import com.appringer.remoteLogger.repo.network.NetworkHelper
import com.appringer.remoteLogger.repo.storage.StorageRepoImp
import com.appringer.remoteLogger.util.Util.getSimCount
import com.appringer.remoteLogger.util.Util.hasNetwork
import io.realm.Realm
import io.realm.RealmConfiguration
import org.json.JSONObject

object LoggerImpl : LogProvider,LoggerConfig {

    override fun register(context: Context,apiKey: String, enableLogCat: Boolean, defaultTag: String) {
        if (enableLogCat) enableLogCat() else disableLogCat()
        AppConfig.API_KEY = apiKey
        AppRingerExceptionHandler.register(context)
        AppConfig.networkStatus = context.hasNetwork()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            AppConfig.simCount = context.getSimCount()
        }
        intiRealm(context,apiKey)
        pushUnSyncedLogs()
    }

    private fun intiRealm(context: Context,apiKey: String){
        Realm.init(context.applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
            .name("$apiKey.realm")
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

    }

    override fun enableLogCat() {
        AppConfig.isLogCatEnable = true
    }

    override fun disableLogCat() {
        AppConfig.isLogCatEnable = false
    }

    override fun unregister() {
        AppRingerExceptionHandler.unregister()
    }

    override fun setTag(tag: String) {
        AppConfig.defaultTag = tag
    }

    override fun setLevel(level: LogLevelEnum) {
        AppConfig.defaultLevel = level
    }

    override fun sendLog(
        message: JSONObject?,
        tag: String?,
        desc: String?,
        logLevelEnum: LogLevelEnum?
    ){
        if(AppConfig.isLogCatEnable){
            LoggerHelper.log(tag?:AppConfig.defaultTag,message.toString())
        }
        NetworkHelper.sendLog(message, tag, desc, logLevelEnum)

    }

    override fun sendLog(t: Throwable,
                tag: String?,
                desc: String?,
                logLevelEnum: LogLevelEnum?){
        if(AppConfig.isLogCatEnable){
            LoggerHelper.log(tag?:AppConfig.defaultTag,t.stackTrace.toString())
        }
        NetworkHelper.sendLog(t,tag, desc, logLevelEnum)
    }

    private fun pushUnSyncedLogs() {
        StorageRepoImp.getCallLogs(CacheLogStatus.NON_SYNCED){list->
            NetworkHelper.pushUnSyncedLogs(list)
        }
    }

}