package com.takwolf.android.demo.refreshandloadmore.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.TopicListAdapter
import com.takwolf.android.demo.refreshandloadmore.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.TopicPagingViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.setupView

class CNodeActivity : AppCompatActivity() {
    private val viewModel: TopicPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)

        binding.toolbar.setTitle(R.string.cnode)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val loadMoreFooter = LoadMoreFooter.create(layoutInflater, binding.recyclerView)
        val adapter = TopicListAdapter(layoutInflater)
        viewModel.topicsHolder.setupView(this, binding.refreshLayout, loadMoreFooter, adapter)
        loadMoreFooter.addToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = adapter

        viewModel.toastHolder.setupView(this, this)

        setContentView(binding.root)
    }
}
