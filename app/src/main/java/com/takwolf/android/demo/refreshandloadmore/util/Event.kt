package com.takwolf.android.demo.refreshandloadmore.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Event<out T>(private val value: T) {
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

fun <T> StateFlow<T>.observe(owner: LifecycleOwner, collector: FlowCollector<T>) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect(collector)
        }
    }
}
