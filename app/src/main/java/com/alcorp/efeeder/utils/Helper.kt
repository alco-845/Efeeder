package com.alcorp.efeeder.utils

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