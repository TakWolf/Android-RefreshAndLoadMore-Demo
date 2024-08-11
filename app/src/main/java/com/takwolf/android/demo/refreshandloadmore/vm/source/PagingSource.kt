package com.takwolf.android.demo.refreshandloadmore.vm.source

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class PagingSource {
    private val refreshState = MutableStateFlow(false)
    private val loadMoreState = MutableStateFlow(LoadMoreFooter.STATE_DISABLED)

    private var dataVersion = 0

    protected fun checkDataVersion(dataVersion: Int): Boolean {
        return this.dataVersion == dataVersion
    }

    fun refresh() {
        if (refreshState.value) {
            return
        }
        refreshState.value = true
        doRefresh(dataVersion)
    }

    protected abstract fun doRefresh(dataVersion: Int)

    protected fun onRefreshSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            this.dataVersion += 1
            refreshState.value = false
            loadMoreState.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            return true
        } else {
            return false
        }
    }

    protected fun onRefreshFailure(dataVersion: Int): Boolean {
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

    protected abstract fun doLoadMore(dataVersion: Int)

    protected fun onLoadMoreSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            return true
        } else {
            return false
        }
    }

    protected fun onLoadMoreFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = LoadMoreFooter.STATE_FAILED
            return true
        } else {
            return false
        }
    }

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
}
