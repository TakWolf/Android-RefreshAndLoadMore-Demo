package com.takwolf.android.demo.refreshandloadmore.model.cnode

import com.takwolf.android.demo.refreshandloadmore.util.JsonUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CNodeClient {
    val api: CNodeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cnodejs.org/api/v1/")
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build())
            .addConverterFactory(MoshiConverterFactory.create(JsonUtils.moshi))
            .build()
        api = retrofit.create(CNodeApi::class.java)
    }
}
