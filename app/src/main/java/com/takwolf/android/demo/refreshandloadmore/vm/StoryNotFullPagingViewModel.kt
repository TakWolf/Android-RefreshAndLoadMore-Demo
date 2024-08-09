package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.Story
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.ZhihuClient
import com.takwolf.android.demo.refreshandloadmore.util.Event
import com.takwolf.android.demo.refreshandloadmore.util.PagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryNotFullPagingViewModel : ViewModel() {
    val stories = MutableStateFlow(emptyList<Story>())
    val errorEvent = MutableLiveData<Event<String>>()

    private var date = ""

    val pagingSource = PagingSource(doRefresh = { dataVersion ->
        viewModelScope.launch {
            try {
                val page = ZhihuClient.api.getLatestStories()
                if (onRefreshSuccess(dataVersion, false)) {
                    stories.value = listOf(page.stories[0])
                    date = page.date
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
                val page = ZhihuClient.api.getStoriesBefore(date)
                if (onLoadMoreSuccess(dataVersion, false)) {
                    stories.value += listOf(page.stories[0])
                    date = page.date
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
