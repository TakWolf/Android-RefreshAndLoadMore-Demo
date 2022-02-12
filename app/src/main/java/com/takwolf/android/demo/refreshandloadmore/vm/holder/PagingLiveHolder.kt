package com.takwolf.android.demo.refreshandloadmore.vm.holder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ListAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class PagingLiveHolder<Entity, PagingParams>(
    private val viewModel: ViewModel,
    private val toastHolder: ToastLiveHolder,
) : ListLiveHolder<Entity>() {
    val refreshStateData = MutableLiveData(false)
    val loadMoreStateData = MutableLiveData(LoadMoreFooter.STATE_DISABLED)

    private var isRefreshDoing = false
    private var isLoadMoreDoing = false
    private var refreshVersion = 0
    private var loadMoreVersion = 0
    private var pagingParams: PagingParams? = null
    private var isFinished = false

    fun refresh() {
        if (!isRefreshDoing) {
            refreshVersion += 1
            refreshStateData.value = true
            isRefreshDoing = true
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                doRefresh(refreshVersion)
            }
        }
    }

    protected abstract suspend fun doRefresh(version: Int)

    protected fun refreshSuccess(version: Int, entities: List<Entity>, pagingParams: PagingParams, isFinished: Boolean) {
        if (refreshVersion == version) {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                loadMoreVersion += 1
                setList(entities)
                this@PagingLiveHolder.pagingParams = pagingParams
                this@PagingLiveHolder.isFinished = isFinished
                isRefreshDoing = false
                refreshStateData.value = false
                isLoadMoreDoing = false
                loadMoreStateData.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            }
        }
    }

    protected fun refreshFailure(version: Int, message: String) {
        if (refreshVersion == version) {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                isRefreshDoing = false
                refreshStateData.value = false
                toastHolder.showToast(message)
            }
        }
    }

    fun loadMore() {
        if (!isLoadMoreDoing && !isFinished) {
            loadMoreStateData.value = LoadMoreFooter.STATE_LOADING
            isLoadMoreDoing = true
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                doLoadMore(loadMoreVersion, pagingParams!!)
            }
        }
    }

    protected abstract suspend fun doLoadMore(version: Int, pagingParams: PagingParams)

    protected fun loadMoreSuccess(version: Int, addedEntities: List<Entity>, pagingParams: PagingParams, isFinished: Boolean) {
        if (loadMoreVersion == version) {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                appendList(addedEntities)
                this@PagingLiveHolder.pagingParams = pagingParams
                this@PagingLiveHolder.isFinished = isFinished
                isLoadMoreDoing = false
                loadMoreStateData.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            }
        }
    }

    protected fun loadMoreFailure(version: Int, message: String) {
        if (loadMoreVersion == version) {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                isLoadMoreDoing = false
                loadMoreStateData.value = LoadMoreFooter.STATE_FAILED
                toastHolder.showToast(message)
            }
        }
    }

    fun resetPaging() {
        refreshVersion += 1
        loadMoreVersion += 1
        isRefreshDoing = false
        isLoadMoreDoing = false
        pagingParams = null
        isFinished = false
        refreshStateData.value = false
        loadMoreStateData.value = LoadMoreFooter.STATE_DISABLED
        clearList()
    }
}

fun <Entity> PagingLiveHolder<Entity, *>.setupView(
    owner: LifecycleOwner,
    adapter: ListAdapter<Entity, *>,
    refreshLayout: SwipeRefreshLayout,
    loadMoreFooter: LoadMoreFooter,
) {
    setupView(owner, adapter)
    refreshStateData.observe(owner) {
        it?.let { isRefreshing ->
            refreshLayout.isRefreshing = isRefreshing
        }
    }
    loadMoreStateData.observe(owner) {
        it?.let { state ->
            loadMoreFooter.state = state
        }
    }
    refreshLayout.setOnRefreshListener {
        refresh()
    }
    loadMoreFooter.setOnLoadMoreListener {
        loadMore()
    }
}
