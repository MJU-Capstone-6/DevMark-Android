package com.devmark.devmark.presentation.view.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.domain.model.Recommend
import com.devmark.devmark.databinding.ItemBookmarkBinding
import com.devmark.devmark.domain.model.workspace.RealRecommendPost
import com.devmark.devmark.domain.model.workspace.RecommendLink

class RecommendRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var recommendList = mutableListOf<RealRecommendPost>()
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

    fun setData(list: List<RealRecommendPost>) {
        recommendList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class RecommendHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RealRecommendPost) {
            binding.tvBookMarkTitle.text = item.title
            binding.tvBookMarkCategory.text = item.categoryName

            itemView.setOnClickListener {
                // 링크 이동
            }
        }
    }

}