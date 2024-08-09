package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.refreshandloadmore.model.local.Photo
import com.takwolf.android.demo.refreshandloadmore.util.PagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoNotFullPagingViewModel : ViewModel() {
    val photos = MutableStateFlow(emptyList<Photo>())

    private var pageNum = -1

    val pagingSource = PagingSource(doRefresh = { dataVersion ->
        viewModelScope.launch {
            val page = Photo.getPageAsync(pageSize = 1)
            if (onRefreshSuccess(dataVersion, !page.hasMore)) {
                photos.value = page.list
                pageNum = 0
            }
        }
    }, doLoadMore = { dataVersion ->
        viewModelScope.launch {
            val page = Photo.getPageAsync(pageNum + 1, 1)
            if (onLoadMoreSuccess(dataVersion, !page.hasMore)) {
                photos.value += page.list
                pageNum += 1
            }
        }
    })

    init {
        pagingSource.refresh()
    }
}
