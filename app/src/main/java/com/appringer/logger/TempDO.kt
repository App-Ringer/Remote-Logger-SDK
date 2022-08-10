package com.appringer.logger

data class TempDO(
    val testName:String,
    val testDesc:String,
    val logDT: Long = System.currentTimeMillis()
)
