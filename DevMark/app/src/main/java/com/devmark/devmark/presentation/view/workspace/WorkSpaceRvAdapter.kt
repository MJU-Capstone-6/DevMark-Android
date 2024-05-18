package com.devmark.devmark.presentation.view.workspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.domain.model.BookMark
import com.devmark.devmark.databinding.ItemBookmarkBinding

class WorkSpaceRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bookmarkList = mutableListOf<BookMark>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookmarkHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookmarkHolder) {
            holder.bind(bookmarkList[position])
        }
    }

    fun setData(list: ArrayList<BookMark>) {
        bookmarkList = list
    }

    inner class BookmarkHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookMark) {
            binding.tvBookMarkTitle.text = item.title
            binding.tvBookMarkCategory.text = item.category

            itemView.setOnClickListener {
                itemClick.onClick(-1) // todo 북마크 Id로 변경 필요
            }
        }
    }

    lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }
}