package com.takwolf.android.demo.refreshandloadmore.vm.holder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.takwolf.android.demo.refreshandloadmore.util.Event

class ToastLiveHolder {
    val toastEvent = MutableLiveData<Event<String>>()

    fun showToast(message: String) {
        toastEvent.value = Event(message)
    }
}

fun ToastLiveHolder.setupView(
    owner: LifecycleOwner,
    context: Context,
) {
    toastEvent.observe(owner) { event ->
        event.handleContent()?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
