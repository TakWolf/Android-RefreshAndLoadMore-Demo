package com.takwolf.android.demo.refreshandloadmore.vm

import android.util.Log
import com.takwolf.android.demo.refreshandloadmore.data.zhihu.Story
import com.takwolf.android.demo.refreshandloadmore.data.zhihu.ZhihuClient
import java.util.*

class StoryNotFullPagingViewModel : PagingViewModel<Story, String>() {
    companion object {
        private const val TAG = "StoryPaging"
    }

    init {
        refresh()
    }

    override suspend fun doRefresh() {
        try {
            val storyPage = ZhihuClient.api.getLatestStories()
            refreshSuccess(Collections.singletonList(storyPage.stories[0]), storyPage.date, false)
        } catch (e: Exception) {
            Log.e(TAG, "doRefresh", e)
            refreshFailure()
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: String) {
        try {
            val storyPage = ZhihuClient.api.getStoriesBefore(pagingParams)
            loadMoreSuccess(version, Collections.singletonList(storyPage.stories[0]), storyPage.date, false)
        } catch (e: Exception) {
            Log.e(TAG, "doLoadMore", e)
            loadMoreFailure(version)
        }
    }
}
