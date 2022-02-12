package com.takwolf.android.demo.refreshandloadmore.vm.holder

import android.util.Log
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.refreshandloadmore.data.zhihu.Story
import com.takwolf.android.demo.refreshandloadmore.data.zhihu.ZhihuClient

class StoryPagingLiveHolder(
    viewModel: ViewModel,
    toastHolder: ToastLiveHolder,
    private val isOneMode: Boolean = false,
) : PagingLiveHolder<Story, String>(
    viewModel,
    toastHolder,
) {
    companion object {
        private const val TAG = "StoryPagingLiveHolder"
    }

    init {
        refresh()
    }

    override suspend fun doRefresh(version: Int) {
        try {
            val storyPage = ZhihuClient.api.getLatestStories()
            val stories = if (isOneMode) arrayListOf(storyPage.stories[0]) else storyPage.stories
            refreshSuccess(version, stories, storyPage.date, false)
        } catch (e: Exception) {
            Log.e(TAG, "doRefresh", e)
            refreshFailure(version, e.message ?: "refresh error")
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: String) {
        try {
            val storyPage = ZhihuClient.api.getStoriesBefore(pagingParams)
            val stories = if (isOneMode) arrayListOf(storyPage.stories[0]) else storyPage.stories
            loadMoreSuccess(version, stories, storyPage.date, false)
        } catch (e: Exception) {
            Log.e(TAG, "doLoadMore", e)
            loadMoreFailure(version, e.message ?: "load more error")
        }
    }
}
