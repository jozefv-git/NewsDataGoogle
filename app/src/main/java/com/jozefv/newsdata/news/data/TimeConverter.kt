package com.jozefv.newsdata.news.data

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun timeConverter(time: String): String {
    val timeFromServer = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    // Time from server is UTC time
    val utcDateTime = LocalDateTime.parse(time, timeFromServer).atZone(ZoneId.of("UTC"))
    // UTC to system's default time zone
    val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())
    // Format to desired style
    val localFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss", Locale.getDefault())
    return localDateTime.format(localFormat)
}