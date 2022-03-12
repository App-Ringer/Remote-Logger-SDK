package com.appringer.remote_logger.model


import com.google.gson.annotations.SerializedName

data class LogResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: Any?,
    @SerializedName("error")
    var error: Any?,
    @SerializedName("message")
    var message: String?
)