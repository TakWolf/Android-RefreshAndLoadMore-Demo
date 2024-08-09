package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.refreshandloadmore.model.cnode.CNodeClient
import com.takwolf.android.demo.refreshandloadmore.model.cnode.Topic
import com.takwolf.android.demo.refreshandloadmore.util.Event
import com.takwolf.android.demo.refreshandloadmore.util.PagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TopicNotFullPagingViewModel : ViewModel() {
    val topics = MutableStateFlow(emptyList<Topic>())
    val errorEvent = MutableLiveData<Event<String>>()

    private var page = 0

    val pagingSource = PagingSource(doRefresh = { dataVersion ->
        viewModelScope.launch {
            try {
                val result = CNodeClient.api.getTopics(limit = 1)
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
    }, doLoadMore = { dataVersion ->
        viewModelScope.launch {
            try {
                val result = CNodeClient.api.getTopics(page = page + 1, limit = 1)
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
    })

    init {
        pagingSource.refresh()
    }
}
