package com.takwolf.android.demo.refreshandloadmore.model.local

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

data class Photo(
    val id: String,
    val url: String,
) {
    companion object {
        private val URLS = arrayOf(
            "https://static.takwolf.com/app-test/minami-kotori/0.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/1.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/2.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/3.png",
            "https://static.takwolf.com/app-test/minami-kotori/4.png",
            "https://static.takwolf.com/app-test/minami-kotori/5.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/6.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/7.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/8.png",
            "https://static.takwolf.com/app-test/minami-kotori/9.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/10.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/11.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/12.png",
            "https://static.takwolf.com/app-test/minami-kotori/13.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/14.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/15.png",
            "https://static.takwolf.com/app-test/minami-kotori/16.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/17.jpg",
            "https://static.takwolf.com/app-test/minami-kotori/18.png",
            "https://static.takwolf.com/app-test/minami-kotori/19.jpg",
        )

        fun new(): Photo {
            return Photo(UUID.randomUUID().toString(), URLS[abs(Random.nextInt() % URLS.size)])
        }

        fun newList(size: Int): List<Photo> {
            return List(size) { new() }
        }

        suspend fun getPageAsync(pageNum: Int = 0, pageSize: Int = 20): Page<Photo> = coroutineScope {
            delay(1000)
            val remaining = max(100 - pageNum * pageSize, 0)
            if (remaining > pageSize) {
                Page(newList(pageSize), true)
            } else {
                Page(newList(remaining), false)
            }
        }
    }
}
