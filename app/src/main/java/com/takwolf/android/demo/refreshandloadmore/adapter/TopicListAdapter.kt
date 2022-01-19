package com.takwolf.android.demo.refreshandloadmore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.data.cnode.Topic
import com.takwolf.android.demo.refreshandloadmore.databinding.ItemTopicBinding
import com.takwolf.android.demo.refreshandloadmore.util.FormatUtils

class TopicListAdapter : ListAdapter<Topic, TopicListAdapter.ViewHolder>(TopicDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(topic: Topic) {
            binding.tvTab.text = topic.tabDisplayString
            binding.tvReplyAndVisitCount.text = "${topic.replyCount} 评论 / ${topic.visitCount} 阅读"
            binding.tvReplyTime.text = "新评论：${FormatUtils.getRelativeTimeSpanString(topic.lastReplyAt)}"
            binding.tvTitle.text = topic.title
            binding.tvSummary.text = topic.content
            binding.imgAuthor.load(FormatUtils.getCNodeCompatAvatarUrl(topic.author.avatarUrl)) {
                placeholder(R.color.image_placeholder)
                transformations(CircleCropTransformation())
            }
            binding.tvAuthor.text = topic.author.loginName
            binding.tvCreateTime.text = "创建于：${FormatUtils.getRelativeTimeSpanString(topic.createAt)}"
        }
    }
}

private object TopicDiffItemCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem == newItem
    }
}
