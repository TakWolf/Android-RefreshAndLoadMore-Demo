package com.takwolf.android.demo.refreshandloadmore.data.local

import java.util.*
import kotlin.math.abs
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

        fun getList(size: Int = 100): MutableList<Photo> {
            val list = ArrayList<Photo>()
            repeat(size) {
                val position = abs(Random.nextInt() % URLS.size)
                list.add(Photo(UUID.randomUUID().toString(), URLS[position]))
            }
            return list
        }
    }
}
