package com.takwolf.android.demo.refreshandloadmore.vm.holder

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.refreshandloadmore.model.local.Photo
import kotlinx.coroutines.delay

class PhotoPagingLiveHolder(
    viewModel: ViewModel,
    toastHolder: ToastLiveHolder,
    private val pageSize: Int = 20,
) : PagingLiveHolder<Photo, Int>(
    viewModel,
    toastHolder,
) {
    init {
        refresh()
    }

    override suspend fun doRefresh(version: Int) {
        delay(1000L)
        refreshSuccess(version, Photo.getList(pageSize), 1, false)
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        delay(1000L)
        val nextPage = pagingParams + 1
        loadMoreSuccess(version, Photo.getList(pageSize), nextPage, nextPage >= 10)
    }
}
