package com.takwolf.android.demo.refreshandloadmore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ItemStoryBinding
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.Story

class StoryListAdapter : ListAdapter<Story, StoryListAdapter.ViewHolder>(StoryDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemStoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.tvTitle.text = story.title
            binding.tvHint.text = story.hint
            story.images?.also { images ->
                binding.imgPhoto.isVisible = true
                binding.imgPhoto.load(images[0]) {
                    placeholder(R.color.image_placeholder)
                    error(R.color.image_placeholder)
                }
            } ?: run {
                binding.imgPhoto.isVisible = false
            }
        }
    }
}

private object StoryDiffItemCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }
}
