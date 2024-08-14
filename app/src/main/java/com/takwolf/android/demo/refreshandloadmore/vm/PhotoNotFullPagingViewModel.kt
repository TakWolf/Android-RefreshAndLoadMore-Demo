package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.refreshandloadmore.vm.source.PhotoPagingSource
import com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter
import kotlinx.coroutines.launch

class PhotoNotFullPagingViewModel : ViewModel() {
    private val pagingSource = PhotoPagingSource(viewModelScope, 1)

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
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingSource.photos.collect { photos ->
                    adapter.submitList(photos)
                }
            }
        }
    }
}
