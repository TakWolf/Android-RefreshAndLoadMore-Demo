package com.takwolf.android.demo.refreshandloadmore.util

import android.content.Context
import android.widget.Toast
import java.lang.ref.WeakReference

private var currentToast: WeakReference<Toast>? = null

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    currentToast?.get()?.cancel()
    Toast.makeText(applicationContext, text, duration).apply {
        show()
        currentToast = WeakReference(this)
    }
}
