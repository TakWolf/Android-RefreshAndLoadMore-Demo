package com.takwolf.android.demo.refreshandloadmore.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.StoryListAdapter
import com.takwolf.android.demo.refreshandloadmore.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.StoryNotFullPagingViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.setupView

class ZhihuNotFullActivity : AppCompatActivity() {
    private val viewModel: StoryNotFullPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.toastHolder.setupView(this, this)

        binding.toolbar.setTitle(R.string.zhihu_not_full)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView)
        val adapter = StoryListAdapter()
        viewModel.storiesHolder.setupView(this, adapter, binding.refreshLayout, loadMoreFooter)
        loadMoreFooter.addToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = adapter
    }
}
