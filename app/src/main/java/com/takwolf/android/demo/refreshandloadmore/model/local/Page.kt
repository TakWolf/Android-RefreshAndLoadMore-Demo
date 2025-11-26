package com.takwolf.android.demo.refreshandloadmore.model.local

data class Page<Data>(
    val list: List<Data>,
    val hasMore: Boolean,
)
