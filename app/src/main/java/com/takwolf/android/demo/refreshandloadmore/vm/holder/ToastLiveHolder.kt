package com.takwolf.android.demo.refreshandloadmore.vm.holder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.hadilq.liveevent.LiveEvent

class ToastLiveHolder {
    val toastEvent = LiveEvent<String>()

    fun showToast(message: String) {
        toastEvent.value = message
    }
}

fun ToastLiveHolder.setupView(
    owner: LifecycleOwner,
    context: Context,
) {
    toastEvent.observe(owner) { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
