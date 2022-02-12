package com.takwolf.android.demo.refreshandloadmore.model.cnode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    @Json(name = "loginname") val loginName: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
)
