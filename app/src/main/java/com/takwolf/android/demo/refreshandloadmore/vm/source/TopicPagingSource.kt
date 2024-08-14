package com.takwolf.android.demo.refreshandloadmore.vm.source

import androidx.lifecycle.MutableLiveData
import com.takwolf.android.demo.refreshandloadmore.model.cnode.CNodeClient
import com.takwolf.android.demo.refreshandloadmore.model.cnode.Topic
import com.takwolf.android.demo.refreshandloadmore.util.Event
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TopicPagingSource(
    private val scope: CoroutineScope,
    private val limit: Int = 20,
) : PagingSource() {
    val topics = MutableStateFlow(emptyList<Topic>())
    val errorEvent = MutableLiveData<Event<String>>()

    private var page = 0

    override fun doRefresh(dataVersion: Int) {
        scope.launch {
            try {
                val result = CNodeClient.api.getTopics(limit = limit)
                if (onRefreshSuccess(dataVersion, result.data.isEmpty())) {
                    topics.value = result.data
                    page = 1
                }
            } catch (e: Exception) {
                if (onRefreshFailure(dataVersion)) {
                    errorEvent.value = Event(e.message ?: "refresh error")
                }
            }
        }
    }

    override fun doLoadMore(dataVersion: Int) {
        scope.launch {
            try {
                val result = CNodeClient.api.getTopics(page = page + 1, limit = limit)
                if (onLoadMoreSuccess(dataVersion, result.data.isEmpty())) {
                    topics.value += result.data
                    page += 1
                }
            } catch (e: Exception) {
                if (onLoadMoreFailure(dataVersion)) {
                    errorEvent.value = Event(e.message ?: "load more error")
                }
            }
        }
    }
}
