package com.appringer.remote_logger.util

import com.google.gson.Gson
import java.lang.reflect.Type

object GSONUtils {
    fun toString(obj: Any?): String {
        return Gson().toJson(obj)
    }

    fun <T> getObj(s: String?, aClass: Class<T>?): T {
        return Gson().fromJson(s, aClass)
    }

    fun <T> getObj(s: String?, type: Type?): T {
        return Gson().fromJson(s, type)
    }

    fun <T> copy(src: Any?, destType: Type?): T {
        return getObj(toString(src), destType)
    }
}