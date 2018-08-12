package eu.gitcode.musicmap.common.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkActive(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}
