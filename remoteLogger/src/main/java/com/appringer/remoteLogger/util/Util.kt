package com.appringer.remoteLogger.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object Util {
    private const val NO_INTERNET = "No Internet"
    private const val WIFI = "WIFI"
    private const val MOBILE_DATA = "Mobile Data"

    fun Context.hasNetwork(): String {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkConnected(connectivityManager)
        } else {
            checkConnectedLegacy(connectivityManager)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkConnected(connectivityManager: ConnectivityManager): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork =
                connectivityManager.activeNetwork

            activeNetwork ?: return NO_INTERNET
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            capabilities ?: return NO_INTERNET
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return MOBILE_DATA
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return WIFI
            }
            return NO_INTERNET
        } else {
            return NO_INTERNET
        }
    }

    private fun checkConnectedLegacy(connectivityManager: ConnectivityManager): String {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo ?: return NO_INTERNET
        if (networkInfo.isConnected) {
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return WIFI
            }
            if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return MOBILE_DATA
            }
        }
        return NO_INTERNET
    }
}