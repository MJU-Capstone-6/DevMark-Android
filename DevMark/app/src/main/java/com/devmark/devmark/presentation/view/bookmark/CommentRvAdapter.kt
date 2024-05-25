package com.devmark.devmark.presentation.view.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.databinding.ItemCommentBinding
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.userId
import com.devmark.devmark.presentation.utils.TimeUtil
import com.devmark.devmark.presentation.view.workspace.OnItemClickListener

class CommentRvAdapter : RecyclerView.Adapter<CommentRvAdapter.CommentHolder>() {

    private var commentList = mutableListOf<CommentEntity>()
    interface OnCommentClickListener {
        fun onClick(type: String, item: CommentEntity)
    }
    private lateinit var itemClickListener: OnCommentClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentHolder(binding)
    }

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(commentList[position])
    }

    fun setData(list: List<CommentEntity>) {
        commentList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setItemClickListener(onItemClickListener: OnCommentClickListener) {
        this.itemClickListener = onItemClickListener
    }

    inner class CommentHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentEntity) {
            binding.apply {
                tvName.text = item.username
                tvComment.text = item.commentContext
                tvTime.text = TimeUtil.formatCreateAt(item.createdAt)

                if (userId == item.userId) {
                    groupBtn.visibility = View.VISIBLE
                    setButtonListeners(item)
                } else {
                    groupBtn.visibility = View.GONE
                }
            }
        }

        private fun setButtonListeners(item: CommentEntity) {
            binding.btnModify.setOnClickListener {
                itemClickListener.onClick("modify", item)
            }

            binding.btnDelete.setOnClickListener {
                itemClickListener.onClick("delete", item)
            }
        }
    }
}
