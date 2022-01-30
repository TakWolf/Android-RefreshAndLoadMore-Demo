package com.takwolf.android.demo.refreshandloadmore.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.adapter.StoryListAdapter
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.refreshandloadmore.helper.PagingViewHelper
import com.takwolf.android.demo.refreshandloadmore.holder.LoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.StoryPagingViewModel

class ZhihuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: StoryPagingViewModel by viewModels()

        binding.toolbar.setTitle(R.string.zhihu)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView)
        val adapter = StoryListAdapter()
        PagingViewHelper.listen(this, viewModel, binding.refreshLayout, loadMoreFooter, adapter)
        loadMoreFooter.addToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = adapter
    }
}