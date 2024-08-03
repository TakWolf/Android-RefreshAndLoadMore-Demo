package com.takwolf.android.demo.refreshandloadmore.util

open class Event<T>(val content: T) {
    var isHandled = false
        private set

    fun handleContent(): T? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            content
        }
    }
}
