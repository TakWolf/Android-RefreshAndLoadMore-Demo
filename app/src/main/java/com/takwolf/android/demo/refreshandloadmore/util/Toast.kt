package com.takwolf.android.demo.refreshandloadmore.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import java.lang.ref.WeakReference

private var currentToast: WeakReference<Toast>? = null

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    currentToast?.get()?.cancel()
    Toast.makeText(applicationContext, text, duration).apply {
        show()
        currentToast = WeakReference(this)
    }
}

fun Context.showToast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_SHORT) {
    currentToast?.get()?.cancel()
    Toast.makeText(applicationContext, stringId, duration).apply {
        show()
        currentToast = WeakReference(this)
    }
}