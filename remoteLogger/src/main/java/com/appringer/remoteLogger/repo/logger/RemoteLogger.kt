package com.appringer.remoteLogger.repo.logger

import android.content.Context
import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.helper.AppConfig
import com.appringer.remoteLogger.model.CacheLogDO
import com.google.gson.JsonObject
import org.json.JSONObject
import java.lang.Exception

object RemoteLogger:LogProvider,LoggerConfig {

    override fun unregister() {
        LoggerImpl.unregister()
    }

    override fun setTag(tag: String) {
        LoggerImpl.setTag(tag)
    }

    override fun setLevel(level: LogLevelEnum) {
        LoggerImpl.setLevel(level)
    }

    override fun register(
        context: Context,
        apiKey: String,
        enableLogCat: Boolean,
        defaultTag: String
    ) {
        LoggerImpl.register(context, apiKey, enableLogCat, defaultTag)
    }

    override fun enableLogCat() { LoggerImpl.enableLogCat() }
    override fun disableLogCat() { LoggerImpl.disableLogCat() }

    fun d(message: String, tag: String? = null, obj:JSONObject? = null) {
        sendLog(obj, tag, message,LogLevelEnum.DEBUG)
    }

    fun d(message: String? = null, tag: String? = null,t:Throwable) {
        sendLog(t, tag, message,LogLevelEnum.DEBUG)
    }

    fun i(message: String, tag: String? = null, obj:JSONObject? = null) {
        sendLog(obj, tag, message,LogLevelEnum.INFO)
    }

    fun i(message: String? = null, tag: String? = null,t:Throwable) {
        sendLog(t, tag, message,LogLevelEnum.INFO)
    }

    fun v(message: String, tag: String? = null, obj:JSONObject? = null) {
        sendLog(obj, tag, message,LogLevelEnum.VERBOSE)
    }

    fun v(message: String? = null, tag: String? = null, t:Throwable) {
        sendLog(t, tag, message,LogLevelEnum.VERBOSE)
    }

    fun e(message: String, tag: String? = null, obj:JSONObject? = null) {
        sendLog(obj, tag, message,LogLevelEnum.ERROR)
    }

    fun e(message: String? = null, tag: String? = null,t:Throwable) {
        sendLog(t, tag, message,LogLevelEnum.ERROR)
    }

    fun log(message: String, tag: String? = null, obj:JSONObject? = null, level: LogLevelEnum = LogLevelEnum.INFO) {
        sendLog(obj,tag,message, level)
    }

    //level: CRASH
    fun log(t:Throwable) {
        sendLog(t)
    }

    fun debug(t:Throwable) {
        sendLog(t, logLevelEnum = LogLevelEnum.DEBUG)
    }

    fun info(t:Throwable) {
        sendLog(t, logLevelEnum = LogLevelEnum.INFO)
    }

    fun error(t:Throwable) {
        sendLog(t, logLevelEnum = LogLevelEnum.ERROR)
    }

    fun log(exception: Exception){
        sendLog(exception)
    }

    fun log(desc: String?,json:JSONObject,tag: String?=AppConfig.defaultTag){
        sendLog(desc = desc, message =json,tag = tag)
    }

    fun debug(desc: String?,json:JSONObject,tag: String?=AppConfig.defaultTag){
        sendLog(desc = desc, message =json,tag = tag, logLevelEnum = LogLevelEnum.DEBUG)
    }

    fun info(desc: String?,json:JSONObject,tag: String?=AppConfig.defaultTag){
        sendLog(desc = desc, message =json,tag = tag, logLevelEnum = LogLevelEnum.INFO)
    }

    fun error(desc: String?,json:JSONObject,tag: String?=AppConfig.defaultTag){
        sendLog(desc = desc, message =json,tag = tag, logLevelEnum = LogLevelEnum.ERROR)
    }

    override fun sendLog(
        message: JSONObject?,
        tag: String?,
        desc: String?,
        logLevelEnum: LogLevelEnum?
    ) {
        LoggerImpl.sendLog(message,tag,desc, logLevelEnum)
    }

    override fun sendLog(t: Throwable, tag: String?, desc: String?, logLevelEnum: LogLevelEnum?) {
        LoggerImpl.sendLog(t,tag, desc, logLevelEnum)
    }

}