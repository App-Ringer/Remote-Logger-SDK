package com.appringer.remote_logger.model

data class LogRequest(
    val level: String,
    val tag: String,
    val description: String,
    val json: String,
    val logDT: Long = System.currentTimeMillis(),
)
