package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.Story
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.ZhihuClient
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.StoryListAdapter
import com.takwolf.android.demo.refreshandloadmore.util.Event
import com.takwolf.android.demo.refreshandloadmore.util.observe
import com.takwolf.android.demo.refreshandloadmore.util.showToast
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryPagingViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val notFullPage = savedStateHandle.get<Boolean>("notFullPage") ?: false

    private val stories = MutableStateFlow(emptyList<Story>())
    private val errorEvent = MutableLiveData<Event<String>>()

    private var date = ""

    private val pagingSource = object : PagingSource() {
        override fun doRefresh(dataVersion: Int) {
            viewModelScope.launch {
                try {
                    val page = ZhihuClient.api.getLatestStories()
                    if (onRefreshSuccess(dataVersion, false)) {
                        stories.value = if (notFullPage) listOf(page.stories[0]) else page.stories
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
            viewModelScope.launch {
                try {
                    val page = ZhihuClient.api.getStoriesBefore(date)
                    if (onLoadMoreSuccess(dataVersion, false)) {
                        stories.value += if (notFullPage) listOf(page.stories[0]) else page.stories
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

    init {
        pagingSource.refresh()
    }

    fun setupViews(
        activity: AppCompatActivity,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
        adapter: StoryListAdapter,
    ) {
        pagingSource.setupSwipeRefreshLayout(activity, refreshLayout)
        pagingSource.setupLoadMoreFooter(activity, loadMoreFooter)
        stories.observe(activity) { stories ->
            adapter.submitList(stories)
        }
        errorEvent.observe(activity) { event ->
            event.handleValue()?.let { message ->
                activity.showToast(message)
            }
        }
    }
}
