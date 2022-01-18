package com.takwolf.android.demo.refreshandloadmore.util

import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object FormatUtils {
    private const val MINUTE = 60 * 1000L
    private const val HOUR = 60 * MINUTE
    private const val DAY = 24 * HOUR

    private val displayDateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")

    fun getRelativeTimeSpanString(dateTime: OffsetDateTime): String {
        val offset = Duration.between(dateTime, OffsetDateTime.now()).toMillis()
        return if (offset > DAY) {
            dateTime.format(displayDateTimeFormatter)
        } else if (offset > HOUR) {
            (offset / HOUR).toString() + "小时前"
        } else if (offset > MINUTE) {
            (offset / MINUTE).toString() + "分钟前"
        } else {
            "刚刚"
        }
    }

    fun getCNodeCompatAvatarUrl(url: String?): String? {
        url?.let {
            if (it.startsWith("//gravatar.com/avatar/")) {
                return "https:${url}"
            }
        }
        return url
    }
}
