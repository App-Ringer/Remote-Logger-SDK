package com.appringer.remoteLogger.model

data class LogStackTrace(
    val stackTrace: Array<StackTraceElement>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogStackTrace

        if (!stackTrace.contentEquals(other.stackTrace)) return false

        return true
    }

    override fun hashCode(): Int {
        return stackTrace.contentHashCode()
    }
}
