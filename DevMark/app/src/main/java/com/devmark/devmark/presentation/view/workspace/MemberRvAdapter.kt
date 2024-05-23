package com.devmark.devmark.presentation.view.workspace


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.R
import com.devmark.devmark.databinding.ItemFilterListBinding


class MemberRvAdapter(private val selectedList: List<Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var memberList = mutableListOf<Pair<Int, String>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFilterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemberHolder(binding)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MemberHolder) {
            holder.bind(memberList[position])
        }
    }

    fun setData(list: List<Pair<Int, String>>) {
        memberList = list.toMutableList()
    }

    inner class MemberHolder(val binding: ItemFilterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<Int, String>) {
            binding.tvFilter.isSelected = if (selectedList.contains(item.first)) {
                binding.tvFilter.setTextColor(itemView.context.getColor(R.color.alabaster))
                true
            } else {
                binding.tvFilter.setTextColor(itemView.context.getColor(R.color.rust))
                false
            }

            binding.tvFilter.text = item.second
            binding.tvFilter.setOnClickListener {
                itemClick.onClick(item.first)
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