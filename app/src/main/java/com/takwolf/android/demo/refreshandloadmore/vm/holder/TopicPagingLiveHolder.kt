package com.takwolf.android.demo.refreshandloadmore.vm.holder

import android.util.Log
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.refreshandloadmore.data.cnode.CNodeClient
import com.takwolf.android.demo.refreshandloadmore.data.cnode.Topic

class TopicPagingLiveHolder(
    viewModel: ViewModel,
    toastHolder: ToastLiveHolder,
    private val pageSize: Int = 20,
) : PagingLiveHolder<Topic, Int>(
    viewModel,
    toastHolder,
) {
    companion object {
        private const val TAG = "TopicPagingLiveHolder"
    }

    init {
        refresh()
    }

    override suspend fun doRefresh(version: Int) {
        try {
            val result = CNodeClient.api.getTopics(limit = pageSize)
            refreshSuccess(version, result.data, 1, result.data.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "doRefresh", e)
            refreshFailure(version, e.message ?: "refresh error")
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        try {
            val nextPage = pagingParams + 1
            val result = CNodeClient.api.getTopics(page = nextPage, limit = pageSize)
            loadMoreSuccess(version, result.data, nextPage, result.data.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "doLoadMore", e)
            loadMoreFailure(version, e.message ?: "load more error")
        }
    }
}
