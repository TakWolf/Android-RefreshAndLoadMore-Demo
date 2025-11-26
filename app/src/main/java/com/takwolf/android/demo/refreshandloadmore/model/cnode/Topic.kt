package com.takwolf.android.demo.refreshandloadmore.model.cnode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Topic(
    val id: String,
    @param:Json(name = "author_id") val authorId: String,
    val author: Author,
    val title: String,
    val tab: String?,
    @param:Json(name = "good") val isGood: Boolean,
    @param:Json(name = "top") val isTop: Boolean,
    val content: String,
    @param:Json(name = "visit_count") val visitCount: Int,
    @param:Json(name = "reply_count") val replyCount: Int,
    @param:Json(name = "create_at") val createAt: OffsetDateTime,
    @param:Json(name = "last_reply_at") val lastReplyAt: OffsetDateTime,
) {
    val tabDisplayString: String
        get() {
            return when (tab) {
                "share" -> "分享"
                "ask" -> "问答"
                "job" -> "招聘"
                "dev" -> "客户端测试"
                else -> "未知"
            }
        }
}
