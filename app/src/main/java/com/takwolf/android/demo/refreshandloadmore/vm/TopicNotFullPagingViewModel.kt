package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.ToastLiveHolder
import com.takwolf.android.demo.refreshandloadmore.vm.holder.TopicPagingLiveHolder

class TopicNotFullPagingViewModel : ViewModel() {
    val toastHolder = ToastLiveHolder()
    val topicsHolder = TopicPagingLiveHolder(this, toastHolder, 1)
}
