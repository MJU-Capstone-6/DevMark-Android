package com.devmark.devmark.presentation.view.workspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.databinding.ItemBookmarkBinding
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.presentation.utils.TimeUtil

class WorkSpaceRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bookmarkList = mutableListOf<BookmarksEntity>()
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

    fun setData(list: List<BookmarksEntity>) {
        bookmarkList = list.reversed().toMutableList()
        notifyDataSetChanged()
    }

    inner class BookmarkHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookmarksEntity) {
            binding.tvBookMarkTitle.text = item.title
            binding.tvBookMarkCategory.text = item.categoryName
            binding.tvCreateTime.text = TimeUtil.formatCreateAt(item.createAt)

            itemView.setOnClickListener {
                itemClick.onClick(item.id)
            }
        }
    }

    lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }

    fun reverseList() {
        bookmarkList.reverse()
        notifyDataSetChanged()
    }
}