package com.takwolf.android.demo.refreshandloadmore.vm

import com.takwolf.android.demo.refreshandloadmore.data.local.Photo
import kotlinx.coroutines.delay

class PhotoPagingViewModel : PagingViewModel<Photo, Int>() {
    init {
        refresh()
    }

    override suspend fun doRefresh() {
        delay(1000L)
        refreshSuccess(Photo.getList(20), 1, false)
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        delay(1000L)
        loadMoreSuccess(version, Photo.getList(20), pagingParams + 1, (pagingParams + 1) >= 10)
    }
}
