package com.devmark.devmark.presentation.view.workspace_select

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.databinding.ItemNewWorkspaceBinding
import com.devmark.devmark.databinding.ItemWorkspaceBinding
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.presentation.view.workspace.OnItemClickListener
import com.devmark.devmark.presentation.view.workspace.OnWorkspaceClickListener

class WorkSpaceSelectRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_VIEW_TYPE_NEW_WORKSPACE = 2
    private val ITEM_VIEW_TYPE_WORKSPACE = 1
    private var dataList = mutableListOf<WorkspaceEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_WORKSPACE -> {
                val binding =
                    ItemWorkspaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                WorkspaceHolder(binding)
            }

            ITEM_VIEW_TYPE_NEW_WORKSPACE -> {
                val binding = ItemNewWorkspaceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                NewWorkspaceHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size) {
            ITEM_VIEW_TYPE_NEW_WORKSPACE
        } else {
            ITEM_VIEW_TYPE_WORKSPACE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WorkspaceHolder -> holder.bind(dataList[position])
            is NewWorkspaceHolder -> {
                holder.bind()
            }
        }
    }

    inner class WorkspaceHolder(val binding: ItemWorkspaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorkspaceEntity) {
            binding.loItemWorkspace.setOnClickListener {
                workspaceClick.onWorkspaceClick(item.id, item.name, item.description)
            }

            binding.tvWorkspaceName.text = item.name
            binding.tvWorkspaceDetail.text = item.description
            binding.tvTotalBookmarkNum.text = item.bookmarkCount.toString()
            binding.tvTotalPeopleNum.text = item.userCount.toString()
        }
    }

    inner class NewWorkspaceHolder(val binding: ItemNewWorkspaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnJoinWorkspace.setOnClickListener {
                itemClick.onClickJoin()
            }
            binding.btnCreateWorkspace.setOnClickListener {
                itemClick.onClickCreate()
            }
        }
    }

    fun addItem(item: WorkspaceEntity) {
        dataList.add(item)
        notifyItemInserted(dataList.size - 1)
    }

    fun setData(list: List<WorkspaceEntity>) {
        dataList = list.toMutableList()
        notifyDataSetChanged()
    }

    lateinit var itemClick: OnMethodClickListener
    fun setItemClickListener(onItemClickListener: OnMethodClickListener) {
        this.itemClick = onItemClickListener
    }

    lateinit var workspaceClick: OnWorkspaceClickListener
    fun setWorkspaceClickListener(onWorkspaceClickListener: OnWorkspaceClickListener) {
        this.workspaceClick = onWorkspaceClickListener
    }

}