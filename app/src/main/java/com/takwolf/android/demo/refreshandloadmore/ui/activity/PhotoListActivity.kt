package com.takwolf.android.demo.refreshandloadmore.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.refreshandloadmore.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.refreshandloadmore.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.refreshandloadmore.vm.PhotoPagingViewModel
import com.takwolf.android.demo.refreshandloadmore.vm.holder.setupView

class PhotoListActivity : AppCompatActivity() {
    private val viewModel: PhotoPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.photo_list)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val loadMoreFooter = LoadMoreFooter.create(layoutInflater, binding.recyclerView)
        loadMoreFooter.addToRecyclerView(binding.recyclerView)
        val adapter = PhotoListAdapter(layoutInflater)
        binding.recyclerView.adapter = adapter
        viewModel.photosHolder.setupView(this, binding.refreshLayout, loadMoreFooter, adapter)

        viewModel.toastHolder.setupView(this, this)
    }
}
