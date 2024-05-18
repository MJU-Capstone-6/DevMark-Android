package com.devmark.devmark.presentation.view.workspace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.databinding.ItemCommentBinding
import com.devmark.devmark.domain.model.ResponseCommentEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.userId
import com.devmark.devmark.presentation.utils.TimeUtil

class CommentRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var commentList = mutableListOf<ResponseCommentEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentHolder) {
            holder.bind(commentList[position])
        }
    }

    fun setData(list: List<ResponseCommentEntity>) {
        commentList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class CommentHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseCommentEntity) {
            if(userId == item.userId){
                binding.groupBtn.visibility = View.VISIBLE

                binding.btnModify.setOnClickListener {
                    itemClick.onClick(item.id)
                }

                binding.btnDelete.setOnClickListener {
                    itemClick.onClick(item.id)
                }
            } else {
                binding.groupBtn.visibility = View.GONE
            }

            binding.tvName.text = item.userName
            binding.tvComment.text = item.context
            binding.tvTime.text = TimeUtil.formatCreateAt(item.updatedAt)
        }
    }

    lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }
}