package com.appringer.remoteLogger.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
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

    fun toJSONObject(obj: Any?): JSONObject {
        return JSONObject(toString(obj))
    }

    fun toJsonObject(obj: Any?): JsonObject? {
        val json = toString(obj)
        val jsonElement = JsonParser.parseString(json)
        return jsonElement.asJsonObject
    }

    fun toMap(jsonObj: JSONObject): MutableMap<String, Any> {
        return mutableMapOf<String, Any>().apply {
            jsonObj.keys().forEach {
                put(it, jsonObj[it])
            }
        }
    }
}