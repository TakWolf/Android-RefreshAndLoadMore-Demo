package com.takwolf.android.demo.refreshandloadmore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityDemoBinding
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.StoryListAdapter
import com.takwolf.android.demo.refreshandloadmore.ui.widget.AppLoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.StoryPagingViewModel

class ZhihuActivity : AppCompatActivity() {
    companion object {
        fun open(activity: AppCompatActivity, notFullPage: Boolean = false) {
            val intent = Intent(activity, ZhihuActivity::class.java).apply {
                putExtra("notFullPage", notFullPage)
            }
            activity.startActivity(intent)
        }
    }

    private val viewModel by viewModels<StoryPagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)
        val binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(if (viewModel.notFullPage) R.string.zhihu_not_full_page else R.string.zhihu)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(null)
        val loadMoreFooter = AppLoadMoreFooter.create(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        binding.recyclerView.addFooterView(R.layout.footer_insets_navigation_bars)
        val adapter = StoryListAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.setupViews(this, binding.refreshLayout, loadMoreFooter, adapter)
    }
}
