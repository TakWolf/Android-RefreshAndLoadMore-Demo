package com.takwolf.android.demo.refreshandloadmore.data.zhihu

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoryPage(
    val date: String,
    val stories: List<Story>,
)
