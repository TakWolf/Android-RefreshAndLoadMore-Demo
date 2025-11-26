package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.model.local.Photo
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.refreshandloadmore.util.lifecycle.observe
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import com.takwolf.android.hfrecyclerview.paging.observe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoPagingViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val notFullPage = savedStateHandle.get<Boolean>("notFullPage") ?: false

    private val photos = MutableStateFlow(emptyList<Photo>())

    private var pageNum = -1

    private val pagingSource = object : PagingSource() {
        override fun doRefresh(dataVersion: Int) {
            viewModelScope.launch {
                val page = Photo.getPageAsync(pageSize = if (notFullPage) 1 else 20)
                if (onRefreshSuccess(dataVersion, !page.hasMore)) {
                    photos.value = page.list
                    pageNum = 0
                }
            }
        }

        override fun doLoadMore(dataVersion: Int) {
            viewModelScope.launch {
                val page = Photo.getPageAsync(pageNum + 1, if (notFullPage) 1 else 20)
                if (onLoadMoreSuccess(dataVersion, !page.hasMore)) {
                    photos.value += page.list
                    pageNum += 1
                }
            }
        }
    }

    init {
        pagingSource.refresh()
    }

    fun setupViews(
        owner: LifecycleOwner,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
        adapter: PhotoListAdapter,
    ) {
        refreshLayout.setOnRefreshListener {
            pagingSource.refresh()
        }
        loadMoreFooter.onLoadMoreListener = LoadMoreFooter.OnLoadMoreListener {
            pagingSource.loadMore()
        }
        pagingSource.refreshState.observe(owner, refreshLayout)
        pagingSource.loadMoreState.observe(owner, loadMoreFooter)
        photos.observe(owner) { photos ->
            adapter.submitList(photos)
        }
    }
}
