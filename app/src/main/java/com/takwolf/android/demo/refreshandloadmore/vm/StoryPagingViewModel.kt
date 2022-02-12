package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.StoryPagingLiveHolder
import com.takwolf.android.demo.refreshandloadmore.vm.holder.ToastLiveHolder

class StoryPagingViewModel : ViewModel() {
    val toastHolder = ToastLiveHolder()
    val storiesHolder = StoryPagingLiveHolder(this, toastHolder)
}
