package com.appringer.remoteLogger.util

import android.Manifest
import android.content.Context
import android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.SubscriptionManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


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
        val activeNetwork = connectivityManager.activeNetwork
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
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun Context.getSimCount(): Int {
        val subsManager =
            getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            subsManager.activeSubscriptionInfoList?.size ?: 0
        } else 0

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