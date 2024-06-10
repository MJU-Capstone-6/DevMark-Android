package com.devmark.devmark.presentation.view.remind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.databinding.ItemRemindBookmarkBinding
import com.devmark.devmark.domain.model.user.Bookmark
import com.devmark.devmark.domain.model.user.NotificationEntity
import com.devmark.devmark.presentation.view.workspace.OnItemClickListener

class RemindRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var remindList = mutableListOf<Bookmark>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemRemindBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RemindHolder(binding)
    }

    override fun getItemCount(): Int {
        return remindList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RemindHolder) {
            holder.bind(remindList[position])
        }
    }

    fun setData(list: List<NotificationEntity>) {
        list.forEach { notificationEntity ->
            remindList.addAll(notificationEntity.bookmarks)
        }
        notifyDataSetChanged()
    }

    inner class RemindHolder(val binding: ItemRemindBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Bookmark) {
            binding.loItemBookmark.setOnClickListener {
                itemClick.onClick(item.bookmarkId)
            }

            binding.tvBookMarkTitle.text = item.title
            binding.tvCreateTime.text = ""
        }
    }

    private lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }
}