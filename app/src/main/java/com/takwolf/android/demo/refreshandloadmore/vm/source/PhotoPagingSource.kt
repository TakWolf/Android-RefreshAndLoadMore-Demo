package com.takwolf.android.demo.refreshandloadmore.vm.source

import com.takwolf.android.demo.refreshandloadmore.model.local.Photo
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoPagingSource(
    private val scope: CoroutineScope,
    private val pageSize: Int = 20,
) : PagingSource() {
    val photos = MutableStateFlow(emptyList<Photo>())

    private var pageNum = -1

    override fun doRefresh(dataVersion: Int) {
        scope.launch {
            val page = Photo.getPageAsync(pageSize = pageSize)
            if (onRefreshSuccess(dataVersion, !page.hasMore)) {
                photos.value = page.list
                pageNum = 0
            }
        }
    }

    override fun doLoadMore(dataVersion: Int) {
        scope.launch {
            val page = Photo.getPageAsync(pageNum + 1, pageSize)
            if (onLoadMoreSuccess(dataVersion, !page.hasMore)) {
                photos.value += page.list
                pageNum += 1
            }
        }
    }
}
