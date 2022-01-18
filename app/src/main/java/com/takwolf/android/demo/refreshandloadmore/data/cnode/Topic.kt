package com.takwolf.android.demo.refreshandloadmore.data.cnode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Topic(
    val id: String,
    val author: Author,
    val title: String,
    @Json(name = "last_reply_at") val lastReplyAt: OffsetDateTime,
    @Json(name = "author_id") val authorId: String,
    val tab: String?,
    val content: String?,
    val good: Boolean,
    val top: Boolean,
    @Json(name = "reply_count") val replyCount: Int,
    @Json(name = "visit_count") val visitCount: Int,
    @Json(name = "create_at") val createAt: OffsetDateTime,
) {
    val tabDisplayString: String
    get() {
        return when (tab) {
            "share" -> "分享"
            "ask" -> "问答"
            "job" -> "招聘"
            "dev" -> "测试"
            else -> "全部"
        }
    }
}
