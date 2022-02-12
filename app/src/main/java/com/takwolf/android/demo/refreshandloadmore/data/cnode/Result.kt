package com.takwolf.android.demo.refreshandloadmore.data.cnode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResult<Data>(
    @Json(name = "success") val isSuccessful: Boolean,
    val data: Data,
)
