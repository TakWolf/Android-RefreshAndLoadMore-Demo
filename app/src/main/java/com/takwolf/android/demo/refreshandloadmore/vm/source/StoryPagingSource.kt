package com.takwolf.android.demo.refreshandloadmore.vm.source

import androidx.lifecycle.MutableLiveData
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.Story
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.ZhihuClient
import com.takwolf.android.demo.refreshandloadmore.util.Event
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryPagingSource(
    private val scope: CoroutineScope,
    private val isOneMode: Boolean,
) : PagingSource() {
    val stories = MutableStateFlow(emptyList<Story>())
    val errorEvent = MutableLiveData<Event<String>>()

    private var date = ""

    override fun doRefresh(dataVersion: Int) {
        scope.launch {
            try {
                val page = ZhihuClient.api.getLatestStories()
                if (onRefreshSuccess(dataVersion, false)) {
                    stories.value = if (isOneMode) listOf(page.stories[0]) else page.stories
                    date = page.date
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
                val page = ZhihuClient.api.getStoriesBefore(date)
                if (onLoadMoreSuccess(dataVersion, false)) {
                    stories.value += if (isOneMode) listOf(page.stories[0]) else page.stories
                    date = page.date
                }
            } catch (e: Exception) {
                if (onLoadMoreFailure(dataVersion)) {
                    errorEvent.value = Event(e.message ?: "load more error")
                }
            }
        }
    }
}
