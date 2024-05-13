package com.devmark.devmark.presentation.view.Remind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.data.Remind
import com.devmark.devmark.databinding.ItemRemindBookmarkBinding

class RemindRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var remindList = mutableListOf<Remind>()
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

    fun setData(list: ArrayList<Remind>) {
        remindList = list
    }

    inner class RemindHolder(val binding: ItemRemindBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Remind) {
            binding.tvBookMarkTitle.text = item.title
            binding.tvBookMarkCategory.text = item.category
            binding.tvRemindTime.text = item.time
        }
    }
}