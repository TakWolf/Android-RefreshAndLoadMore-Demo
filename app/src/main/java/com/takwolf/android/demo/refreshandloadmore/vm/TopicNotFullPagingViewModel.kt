package com.takwolf.android.demo.refreshandloadmore.vm

import android.util.Log
import com.takwolf.android.demo.refreshandloadmore.data.cnode.CNodeClient
import com.takwolf.android.demo.refreshandloadmore.data.cnode.Topic

class TopicNotFullPagingViewModel : PagingViewModel<Topic, Int>() {
    companion object {
        private const val TAG = "TopicPaging"
    }

    init {
        refresh()
    }

    override suspend fun doRefresh() {
        try {
            val result = CNodeClient.api.getTopics(page = 1, limit = 1)
            refreshSuccess(result.data, 1, result.data.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "doRefresh", e)
            refreshFailure()
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        try {
            val nextPage = pagingParams + 1
            val result = CNodeClient.api.getTopics(page = nextPage, limit = 1)
            loadMoreSuccess(version, result.data, nextPage, result.data.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "doLoadMore", e)
            loadMoreFailure(version)
        }
    }
}
