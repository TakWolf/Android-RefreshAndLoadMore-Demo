package com.takwolf.android.demo.refreshandloadmore.model.zhihu

import com.takwolf.android.demo.refreshandloadmore.util.JsonUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ZhihuClient {
    val api: ZhihuApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://news-at.zhihu.com/api/4/")
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build())
            .addConverterFactory(MoshiConverterFactory.create(JsonUtils.moshi))
            .build()
        api = retrofit.create(ZhihuApi::class.java)
    }
}
