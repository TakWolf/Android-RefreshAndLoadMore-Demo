package com.takwolf.android.demo.refreshandloadmore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ItemStoryBinding
import com.takwolf.android.demo.refreshandloadmore.model.zhihu.Story

class StoryListAdapter(private val layoutInflater: LayoutInflater) : ListAdapter<Story, StoryListAdapter.ViewHolder>(StoryDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStoryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.tvTitle.text = story.title
            binding.tvHint.text = story.hint
            binding.imgPhoto.load(story.images[0]) {
                placeholder(R.color.image_placeholder)
                error(R.color.image_placeholder)
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
