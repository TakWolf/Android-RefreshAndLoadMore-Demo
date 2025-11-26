package com.takwolf.android.demo.refreshandloadmore.util.lifecycle

open class Event<out T>(private val value: T) {
    private val consumers = mutableSetOf<String?>()

    fun isHandled(consumer: String? = null): Boolean {
        return consumers.contains(consumer)
    }

    fun handleValue(consumer: String? = null): T? {
        return if (isHandled(consumer)) {
            null
        } else {
            consumers.add(consumer)
            value
        }
    }
}
