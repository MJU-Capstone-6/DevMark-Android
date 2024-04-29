package com.devmark.devmark.presentation.Workspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.R
import com.devmark.devmark.data.Category
import com.devmark.devmark.databinding.ItemFilterListBinding

class CategoryRvAdapter(private val selectedList: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var categoryList = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFilterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryHolder) {
            holder.bind(categoryList[position])
        }
    }

    fun setData(list: ArrayList<Category>) {
        categoryList = list
    }

    inner class CategoryHolder(val binding: ItemFilterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.tvFilter.isSelected = if (selectedList.contains(item.category)) {
                binding.tvFilter.setTextColor(itemView.context.getColor(R.color.alabaster))
                true
            } else {
                binding.tvFilter.setTextColor(itemView.context.getColor(R.color.rust))
                false
            }

            binding.tvFilter.text = item.category
            binding.tvFilter.setOnClickListener {
                itemClick.onClick(item.category)
                val newColor =
                    if (!binding.tvFilter.isSelected) {
                        itemView.context.getColor(R.color.alabaster)
                    } else {
                        itemView.context.getColor(R.color.rust)
                    }
                binding.tvFilter.isSelected = !binding.tvFilter.isSelected
                binding.tvFilter.setTextColor(newColor)
            }
        }
    }

    lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }

}