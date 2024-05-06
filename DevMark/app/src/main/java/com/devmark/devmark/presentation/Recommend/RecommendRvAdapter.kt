package com.devmark.devmark.presentation.Recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.data.BookMark
import com.devmark.devmark.databinding.ItemBookmarkBinding

class RecommendRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var recommendList = mutableListOf<BookMark>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendHolder(binding)
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecommendHolder) {
            holder.bind(recommendList[position])
        }
    }

    fun setData(list: ArrayList<BookMark>) {
        recommendList = list
    }

    inner class RecommendHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookMark) {
            binding.tvBookMarkTitle.text = item.title
            binding.tvBookMarkCategory.text = item.category
        }
    }

}