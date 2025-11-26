package com.takwolf.android.demo.refreshandloadmore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.takwolf.android.demo.refreshandloadmore.R
import com.takwolf.android.demo.refreshandloadmore.databinding.ItemTopicBinding
import com.takwolf.android.demo.refreshandloadmore.model.cnode.Topic
import com.takwolf.android.demo.refreshandloadmore.util.timeSpanStringFromNow

class TopicListAdapter : ListAdapter<Topic, TopicListAdapter.ViewHolder>(TopicDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemTopicBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            val resources = itemView.resources
            binding.imgGood.isVisible = topic.isGood
            binding.tvTop.isVisible = topic.isTop
            binding.tvTab.isVisible = !topic.isTop
            binding.tvTab.text = topic.tabDisplayString
            binding.tvReplyAndVisitCount.text = resources.getString(R.string.d_reply_d_visit, topic.replyCount, topic.visitCount)
            binding.tvReplyTime.text = resources.getString(R.string.reply_at_s, topic.lastReplyAt.timeSpanStringFromNow(resources))
            binding.tvTitle.text = topic.title
            binding.tvSummary.text = topic.content
            binding.imgAuthor.load(topic.author.avatarUrlCompat)
            binding.tvAuthor.text = topic.author.loginName
            binding.tvCreateTime.text = resources.getString(R.string.create_at_s, topic.createAt.timeSpanStringFromNow(resources))
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
