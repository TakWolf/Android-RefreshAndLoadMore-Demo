package com.takwolf.android.demo.refreshandloadmore.model.cnode

import retrofit2.http.GET
import retrofit2.http.Query

interface CNodeApi {
    @GET("topics")
    suspend fun getTopics(
        @Query("tab") tab: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("mdrender") mdrender: Boolean = false,
    ): DataResult<List<Topic>>
}
