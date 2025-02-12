package com.rcmiku.music.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import com.rcmiku.ncmapi.utils.base64Encode
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.net.URLEncoder
import java.util.Locale
import java.util.UUID

fun getDeviceID(): String {
    val uuid = UUID.randomUUID().toString().replace("-", "").take(16)
    val deviceID = "null 02:00:00:00:00:00 $uuid unknown"
    return urlEncode(base64Encode(deviceID.toByteArray()))
}

fun <T> getItemShape(
    prevItem: T?,
    nextItem: T?,
    corner: Dp,
    subCorner: Dp,
): RoundedCornerShape {
    val shape = when {
        prevItem != null && nextItem != null -> RoundedCornerShape(subCorner)
        prevItem == null && nextItem == null -> RoundedCornerShape(corner)
        prevItem == null -> RoundedCornerShape(
            topStart = corner, topEnd = corner,
            bottomStart = subCorner, bottomEnd = subCorner
        )

        else -> RoundedCornerShape(
            topStart = subCorner, topEnd = subCorner,
            bottomStart = corner, bottomEnd = corner
        )
    }

    return shape
}

fun formatPlayCount(count: Double): String {
    val locale = Locale.getDefault()
    return when {
        count >= 1_0000_0000 -> String.format(locale, "%.1f亿", count / 1_0000_0000.0)
        count >= 1_0000 -> String.format(locale, "%.1f万", count / 1_0000.0)
        else -> count.toInt().toString()
    }
}

fun formatTimestamp(timestamp: Long): String {
    val localDateTime = Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.year}-${localDateTime.monthNumber}-${localDateTime.dayOfMonth}"
}


fun urlEncode(data: String): String = URLEncoder.encode(data, Charsets.UTF_8.name())

fun makeTimeString(duration: Long?): String {
    if (duration == null || duration < 0) return ""
    var sec = duration / 1000
    val day = sec / 86400
    sec %= 86400
    val hour = sec / 3600
    sec %= 3600
    val minute = sec / 60
    sec %= 60
    return when {
        day > 0 -> "%d:%02d:%02d:%02d".format(day, hour, minute, sec)
        hour > 0 -> "%d:%02d:%02d".format(hour, minute, sec)
        else -> "%d:%02d".format(minute, sec)
    }
}

data class LrcLine(val time: Long, val text: String)

fun String.parseLrc(): List<LrcLine> {
    val regex = """\[(\d+):(\d+)\.(\d{2,3})]\s*(.*)""".toRegex()
    return this.lines().mapNotNull { it ->
        regex.matchEntire(it)?.destructured?.let { (min, sec, ms, text) ->
            val time = min.toLong() * 60 * 1000 + sec.toLong() * 1000 + ms.toLong().let {
                if (ms.length == 2) it * 10 else it
            }
            LrcLine(time, text)
        }
    }
}