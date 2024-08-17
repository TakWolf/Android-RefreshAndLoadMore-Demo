package com.takwolf.android.demo.refreshandloadmore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhotoList.setOnClickListener {
            startActivity(Intent(this, PhotoListActivity::class.java))
        }

        binding.btnPhotoListNotFull.setOnClickListener {
            startActivity(Intent(this, PhotoListNotFullActivity::class.java))
        }

        binding.btnCnode.setOnClickListener {
            startActivity(Intent(this, CNodeActivity::class.java))
        }

        binding.btnCnodeNotFull.setOnClickListener {
            startActivity(Intent(this, CNodeNotFullActivity::class.java))
        }

        binding.btnZhihu.setOnClickListener {
            startActivity(Intent(this, ZhihuActivity::class.java))
        }

        binding.btnZhihuNotFull.setOnClickListener {
            startActivity(Intent(this, ZhihuNotFullActivity::class.java))
        }
    }
}
