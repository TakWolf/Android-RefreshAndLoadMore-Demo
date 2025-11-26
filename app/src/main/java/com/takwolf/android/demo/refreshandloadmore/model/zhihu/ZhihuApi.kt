package com.takwolf.android.demo.refreshandloadmore.model.zhihu

import retrofit2.http.GET
import retrofit2.http.Path

interface ZhihuApi {
    @GET("stories/latest")
    suspend fun getLatestStories(): StoryPage

    @GET("stories/before/{date}")
    suspend fun getStoriesBefore(@Path("date") date: String): StoryPage
}
