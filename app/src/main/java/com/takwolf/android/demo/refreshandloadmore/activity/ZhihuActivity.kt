package com.takwolf.android.demo.refreshandloadmore.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.adapter.StoryListAdapter
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.refreshandloadmore.holder.LoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.StoryPagingViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.setupView

class ZhihuActivity : AppCompatActivity() {
    private val viewModel: StoryPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.toastHolder.setupView(this, this)

        binding.toolbar.setTitle(R.string.zhihu)
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
