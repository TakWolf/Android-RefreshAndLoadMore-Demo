package com.takwolf.android.demo.refreshandloadmore.model.cnode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    @param:Json(name = "loginname") val loginName: String?,
    @param:Json(name = "avatar_url") val avatarUrl: String?,
) {
    val avatarUrlCompat: String?
        get() {
            return avatarUrl?.let {
                if (it.startsWith("//gravatar.com/avatar/")) {
                    "https:${it}"
                } else {
                    it
                }
            }
        }
}
