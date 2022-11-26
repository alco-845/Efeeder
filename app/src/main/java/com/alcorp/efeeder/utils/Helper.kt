package com.alcorp.efeeder.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

fun setTimeFormat(hour: Int, minute: Int): String {
    val newHour: String = if (hour < 10) {
        "0$hour"
    } else {
        hour.toString()
    }
    val newMinute: String = if (minute < 10) {
        "0$minute"
    } else {
        minute.toString()
    }
    return "$newHour:$newMinute"
}

fun Int.setHourFormat(): String {
    val newHour: String = if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
    return newHour
}

fun Int.setMinuteFormat(): String {
    val newMinute: String = if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
    return newMinute
}

fun checkNetwork(context: Context): Boolean{
    val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            cm.getNetworkCapabilities(cm.activeNetwork)
        } else {
            cm.activeNetworkInfo
        }
    return network != null
}