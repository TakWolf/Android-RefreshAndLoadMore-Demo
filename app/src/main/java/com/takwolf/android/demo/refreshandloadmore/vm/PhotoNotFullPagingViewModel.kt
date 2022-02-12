package com.takwolf.android.demo.refreshandloadmore.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.PhotoPagingLiveHolder
import com.takwolf.android.demo.refreshandloadmore.vm.holder.ToastLiveHolder

class PhotoNotFullPagingViewModel : ViewModel() {
    val toastHolder = ToastLiveHolder()
    val photosHolder = PhotoPagingLiveHolder(this, toastHolder, 1)
}
