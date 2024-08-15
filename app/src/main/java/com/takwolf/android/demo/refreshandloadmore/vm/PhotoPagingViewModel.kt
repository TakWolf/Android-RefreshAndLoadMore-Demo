package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.refreshandloadmore.util.observe
import com.takwolf.android.demo.refreshandloadmore.vm.source.PhotoPagingSource
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter

class PhotoPagingViewModel : ViewModel() {
    private val pagingSource = PhotoPagingSource(viewModelScope)

    init {
        pagingSource.refresh()
    }

    fun setupViews(
        owner: LifecycleOwner,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
        adapter: PhotoListAdapter,
    ) {
        pagingSource.setupSwipeRefreshLayout(owner, refreshLayout)
        pagingSource.setupLoadMoreFooter(owner, loadMoreFooter)
        pagingSource.photos.observe(owner) { photos ->
            adapter.submitList(photos)
        }
    }
}
