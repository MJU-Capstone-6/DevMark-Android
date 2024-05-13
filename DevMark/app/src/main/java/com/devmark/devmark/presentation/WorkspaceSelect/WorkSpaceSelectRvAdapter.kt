package com.devmark.devmark.presentation.WorkspaceSelect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmark.devmark.data.WorkSpace
import com.devmark.devmark.databinding.DialogWorkspaceJoinBinding
import com.devmark.devmark.databinding.ItemNewWorkspaceBinding
import com.devmark.devmark.databinding.ItemWorkspaceBinding
import com.devmark.devmark.presentation.Setting.SignOutDialog
import com.devmark.devmark.presentation.Workspace.OnItemClickListener

class WorkSpaceSelectRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_VIEW_TYPE_NEW_WORKSPACE = 2
    private val ITEM_VIEW_TYPE_WORKSPACE = 1
    private var dataList = mutableListOf<WorkSpace>()
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
        fun bind(item: WorkSpace) {
            binding.tvWorkspaceName.text = item.title
            binding.tvWorkspaceDetail.text = item.desc
            binding.tvTotalBookmarkNum.text = item.totalBookmark.toString()
            binding.tvTotalPeopleNum.text = item.totalPeople.toString()
        }
    }

    inner class NewWorkspaceHolder(val binding: ItemNewWorkspaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnEnterWorkspace.setOnClickListener {
                itemClick.onClick("aa")
            }
            binding.btnNewWorkspace.setOnClickListener {

            }
        }
    }


    fun setData(list: ArrayList<WorkSpace>) {
        dataList = list
    }

    lateinit var itemClick: OnItemClickListener
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }

}