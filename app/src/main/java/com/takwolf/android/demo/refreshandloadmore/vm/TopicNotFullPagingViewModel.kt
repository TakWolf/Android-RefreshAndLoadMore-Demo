package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.TopicListAdapter
import com.takwolf.android.demo.refreshandloadmore.util.showToast
import com.takwolf.android.demo.refreshandloadmore.vm.source.TopicPagingSource
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter
import kotlinx.coroutines.launch

class TopicNotFullPagingViewModel : ViewModel() {
    private val pagingSource = TopicPagingSource(viewModelScope, 1)

    init {
        pagingSource.refresh()
    }

    fun setupViews(
        activity: AppCompatActivity,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
        adapter: TopicListAdapter,
    ) {
        pagingSource.setupSwipeRefreshLayout(activity, refreshLayout)
        pagingSource.setupLoadMoreFooter(activity, loadMoreFooter)
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingSource.topics.collect { topics ->
                    adapter.submitList(topics)
                }
            }
        }
        pagingSource.errorEvent.observe(activity) { event ->
            event.handleValue()?.let { message ->
                activity.showToast(message)
            }
        }
    }
}
