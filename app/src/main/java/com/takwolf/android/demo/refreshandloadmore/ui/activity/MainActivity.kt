package com.takwolf.android.demo.refreshandloadmore.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhotoList.setOnClickListener {
            PhotoListActivity.open(this)
        }

        binding.btnPhotoListNotFullPage.setOnClickListener {
            PhotoListActivity.open(this, true)
        }

        binding.btnCnode.setOnClickListener {
            CNodeActivity.open(this)
        }

        binding.btnCnodeNotFullPage.setOnClickListener {
            CNodeActivity.open(this, true)
        }

        binding.btnZhihu.setOnClickListener {
            ZhihuActivity.open(this)
        }

        binding.btnZhihuNotFullPage.setOnClickListener {
            ZhihuActivity.open(this, true)
        }
    }
}
