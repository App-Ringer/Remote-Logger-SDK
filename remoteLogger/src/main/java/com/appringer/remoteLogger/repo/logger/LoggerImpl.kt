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

object LoggerImpl : LogProvider, LoggerConfig {

    override fun register(
        context: Context,
        apiKey: String,
        enableLogCat: Boolean,
        defaultTag: String,
        appBuildVersion: String
    ) {
        if (enableLogCat) enableLogCat() else disableLogCat()
        AppConfig.API_KEY = apiKey

        AppRingerExceptionHandler.register(context)
        AppConfig.NETWORK_STATUS = context.hasNetwork()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            AppConfig.SIM_COUNT = context.getSimCount()
        }
        AppConfig.APP_BUILD_VERSION = appBuildVersion
        intiRealm(context, apiKey)
        pushUnSyncedLogs()
    }

    private fun intiRealm(context: Context, apiKey: String) {
        Realm.init(context.applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
            .name("$apiKey.realm")
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

    }

    override fun enableLogCat() {
        AppConfig.IS_LOGCAT_ENABLE = true
    }

    override fun disableLogCat() {
        AppConfig.IS_LOGCAT_ENABLE = false
    }

    override fun unregister() {
        AppRingerExceptionHandler.unregister()
    }

    override fun setTag(tag: String) {
        AppConfig.DEFAULT_TAG = tag
    }

    override fun setLevel(level: LogLevelEnum) {
        AppConfig.DEFAULT_LEVEL = level
    }

    override fun setDefaultTag(tag: String) {
        AppConfig.DEFAULT_TAG = tag
    }

    override fun setDefaultLevel(level: LogLevelEnum) {
        AppConfig.DEFAULT_LEVEL = level
    }

    override fun sendLog(
        message: JSONObject?,
        tag: String?,
        desc: String?,
        logLevelEnum: LogLevelEnum?
    ) {
        if (AppConfig.IS_LOGCAT_ENABLE) {
            LoggerHelper.log(tag ?: AppConfig.DEFAULT_TAG, message.toString())
        }
        NetworkHelper.sendLog(message, tag, desc, logLevelEnum)

    }

    override fun sendLog(
        t: Throwable,
        tag: String?,
        desc: String?,
        logLevelEnum: LogLevelEnum?
    ) {
        if (AppConfig.IS_LOGCAT_ENABLE) {
            LoggerHelper.log(tag ?: AppConfig.DEFAULT_TAG, t.stackTrace.toString())
        }
        NetworkHelper.sendLog(t, tag, desc, logLevelEnum)
    }

    private fun pushUnSyncedLogs() {
        StorageRepoImp.getCallLogs(CacheLogStatus.NON_SYNCED) { list ->
            NetworkHelper.pushUnSyncedLogs(list)
        }
    }

}