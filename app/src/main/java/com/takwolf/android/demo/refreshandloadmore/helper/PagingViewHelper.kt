package com.takwolf.android.demo.refreshandloadmore.helper

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.refreshandloadmore.vm.PagingViewModel
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter

object PagingViewHelper {
    fun <Entity> listen(
        owner: LifecycleOwner,
        viewModel: PagingViewModel<Entity, *>,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
        adapter: ListAdapter<Entity, *>
    ) {
        viewModel.refreshStateData.observe(owner) {
            it?.let { isRefreshing ->
                refreshLayout.isRefreshing = isRefreshing
            }
        }
        viewModel.loadMoreStateData.observe(owner) {
            it?.let { state ->
                loadMoreFooter.state = state
            }
        }
        viewModel.entitiesData.observe(owner) { entities ->
            adapter.submitList(ArrayList(entities))
        }
        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        loadMoreFooter.setOnLoadMoreListener {
            viewModel.loadMore()
        }
    }
}
