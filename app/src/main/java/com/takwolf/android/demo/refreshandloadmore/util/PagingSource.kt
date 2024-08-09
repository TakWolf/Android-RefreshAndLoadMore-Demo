package com.takwolf.android.demo.refreshandloadmore.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PagingSource(
    private val doRefresh: PagingSource.(dataVersion: Int) -> Unit,
    private val doLoadMore: PagingSource.(dataVersion: Int) -> Unit,
) {
    private var dataVersion = 0

    private val refreshState = MutableStateFlow(false)
    private val loadMoreState = MutableStateFlow(LoadMoreFooter.STATE_DISABLED)

    fun setupViews(
        owner: LifecycleOwner,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
    ) {
        refreshLayout.setOnRefreshListener {
            refresh()
        }
        loadMoreFooter.setOnLoadMoreListener {
            loadMore()
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                refreshState.collect { isRefreshing ->
                    refreshLayout.isRefreshing = isRefreshing
                }
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadMoreState.collect { state ->
                    loadMoreFooter.state = state
                }
            }
        }
    }

    fun checkDataVersion(dataVersion: Int): Boolean {
        return this.dataVersion == dataVersion
    }

    fun refresh() {
        if (refreshState.value) {
            return
        }
        refreshState.value = true
        doRefresh(dataVersion)
    }

    fun onRefreshSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            this.dataVersion += 1
            refreshState.value = false
            loadMoreState.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            return true
        } else {
            return false
        }
    }

    fun onRefreshFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            refreshState.value = false
            return true
        } else {
            return false
        }
    }

    fun loadMore() {
        if (loadMoreState.value == LoadMoreFooter.STATE_DISABLED ||
            loadMoreState.value == LoadMoreFooter.STATE_LOADING ||
            loadMoreState.value == LoadMoreFooter.STATE_FINISHED) {
            return
        }
        loadMoreState.value = LoadMoreFooter.STATE_LOADING
        doLoadMore(dataVersion)
    }

    fun onLoadMoreSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            return true
        } else {
            return false
        }
    }

    fun onLoadMoreFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = LoadMoreFooter.STATE_FAILED
            return true
        } else {
            return false
        }
    }
}
