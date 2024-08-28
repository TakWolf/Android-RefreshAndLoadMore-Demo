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
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.TopicListAdapter
import com.takwolf.android.demo.refreshandloadmore.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.TopicPagingViewModel

class CNodeActivity : AppCompatActivity() {
    companion object {
        fun open(activity: AppCompatActivity, notFullPage: Boolean = false) {
            val intent = Intent(activity, CNodeActivity::class.java).apply {
                putExtra("notFullPage", notFullPage)
            }
            activity.startActivity(intent)
        }
    }

    private val viewModel by viewModels<TopicPagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(if (viewModel.notFullPage) R.string.cnode_not_full_page else R.string.cnode)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        binding.recyclerView.addFooterView(R.layout.footer_insets_navigation_bars)
        val adapter = TopicListAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.setupViews(this, binding.refreshLayout, loadMoreFooter, adapter)
    }
}
