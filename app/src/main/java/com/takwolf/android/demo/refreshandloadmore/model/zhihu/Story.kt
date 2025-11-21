package com.takwolf.android.demo.refreshandloadmore.model.zhihu

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Story(
    val id: String,
    val title: String,
    val hint: String,
    val type: Int,
    val images: List<String>?,
    @param:Json(name = "image_hue") val imageHue: String,
    val url: String,
    @param:Json(name = "ga_prefix") val gaPrefix: String,
)
