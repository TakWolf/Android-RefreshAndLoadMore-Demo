package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.StoryListAdapter
import com.takwolf.android.demo.refreshandloadmore.util.showToast
import com.takwolf.android.demo.refreshandloadmore.vm.source.StoryPagingSource
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter
import kotlinx.coroutines.launch

class StoryPagingViewModel : ViewModel() {
    private val pagingSource = StoryPagingSource(viewModelScope, false)

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
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingSource.stories.collect { stories ->
                    adapter.submitList(stories)
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
