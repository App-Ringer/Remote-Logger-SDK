package com.appringer.remoteLogger.helper

import android.util.Log
import com.appringer.remoteLogger.util.GSONUtils

object LoggerHelper {
    internal const val mErrorTag = "KUSH_ERROR"

    internal fun log(message: String?, ste: StackTraceElement): String {
        val className = ste.className
        val methodName = ste.methodName
        return prepareFinalLog(className, methodName, message, "Info").also { logString ->
            Log.d(mErrorTag, logString)
        }
    }

    internal fun log(
        tag: String?,
        message: String?,
        ste: StackTraceElement,
        vararg strings: String?
    ) {
        val className = ste.className
        val methodName = ste.methodName
        Log.d(mErrorTag, prepareFinalLog(className, methodName, message, tag!!, strings))
    }

    internal fun log(tag: String?, message: String?) {
        Log.d(mErrorTag, prepareFinalLog("", "", message, tag ?: ""))
    }

    internal fun log(message: String?) {
        Log.d(mErrorTag, prepareFinalLog("", "", message))
    }

    internal fun log(ex: Exception?) {
        var ex = ex
        try {
            if (ex == null) {
                ex = Exception()
            }
            val className = ex.stackTrace[1].className
            val methodName = ex.stackTrace[1].methodName
            Log.d(mErrorTag, prepareFinalLog(className, methodName, Log.getStackTraceString(ex)))

        } catch (var3: Exception) {
            Log.d(mErrorTag, var3.toString())
        }
    }

    internal fun prepareFinalLog(
        className: String,
        methodName: String,
        message: String?,
        vararg arguments: Any
    ): String {
        var className = className
        var finalMessage = ""
        val packageArray = className.split("\\.").toTypedArray()
        className = packageArray[packageArray.size - 1]
        try {
            if (message != null) {
                val logBuffer: String = GSONUtils.toString(arguments).toString() + " " + message
                finalMessage = "[$className] [$methodName]  : $logBuffer"
            }
        } catch (var9: Exception) {
            val lineNumber = Thread.currentThread().stackTrace[2].lineNumber
            finalMessage =
                "[" + className + "] [" + methodName + "]  : at line no. " + lineNumber + " " + var9.message
            Log.d(mErrorTag, finalMessage)
        }
        return finalMessage
    }
}