package com.takwolf.android.demo.refreshandloadmore.data.cnode

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result<Data>(
    val data: Data,
)
