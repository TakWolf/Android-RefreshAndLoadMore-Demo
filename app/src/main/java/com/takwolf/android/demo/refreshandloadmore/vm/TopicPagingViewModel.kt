package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.model.cnode.CNodeClient
import com.takwolf.android.demo.refreshandloadmore.model.cnode.Topic
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.TopicListAdapter
import com.takwolf.android.demo.refreshandloadmore.util.lifecycle.Event
import com.takwolf.android.demo.refreshandloadmore.util.lifecycle.observe
import com.takwolf.android.demo.refreshandloadmore.util.showToast
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import com.takwolf.android.hfrecyclerview.paging.observe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TopicPagingViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val notFullPage = savedStateHandle.get<Boolean>("notFullPage") ?: false

    private val topics = MutableStateFlow(emptyList<Topic>())
    private val errorEvent = MutableLiveData<Event<String>>()

    private var page = 0

    private val pagingSource = object : PagingSource() {
        override fun doRefresh(dataVersion: Int) {
            viewModelScope.launch {
                try {
                    val result = CNodeClient.api.getTopics(limit = if (notFullPage) 1 else 20)
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
            viewModelScope.launch {
                try {
                    val result = CNodeClient.api.getTopics(page = page + 1, limit = if (notFullPage) 1 else 20)
                    if (onLoadMoreSuccess(dataVersion, result.data.isEmpty())) {
                        topics.value += result.data
                        page += 1
                    }
                } catch (e: Exception) {
                    delay(1000)
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
        adapter: TopicListAdapter,
    ) {
        refreshLayout.setOnRefreshListener {
            pagingSource.refresh()
        }
        loadMoreFooter.onLoadMoreListener = LoadMoreFooter.OnLoadMoreListener {
            pagingSource.loadMore()
        }
        pagingSource.refreshState.observe(activity, refreshLayout)
        pagingSource.loadMoreState.observe(activity, loadMoreFooter)
        topics.observe(activity) { topics ->
            adapter.submitList(topics)
        }
        errorEvent.observe(activity) { event ->
            event.handleValue()?.let { message ->
                activity.showToast(message)
            }
        }
    }
}
