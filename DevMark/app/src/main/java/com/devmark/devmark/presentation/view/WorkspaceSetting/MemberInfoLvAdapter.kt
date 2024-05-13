package com.devmark.devmark.presentation.view.WorkspaceSetting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.devmark.devmark.R
import com.devmark.devmark.data.Member
import com.devmark.devmark.databinding.ItemMemberInfoBinding

class MemberInfoLvAdapter(val context: Context, private val memberList: List<Member>) :
    BaseAdapter() {
    private var mBinding: ItemMemberInfoBinding? = null
    private val binding get() = mBinding!!

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        mBinding = ItemMemberInfoBinding.inflate(LayoutInflater.from(context))

        val params = ConstraintLayout.LayoutParams(
            MATCH_PARENT,
            (32 * binding.clItemMember.resources.displayMetrics.density).toInt()
        )
        binding.clItemMember.layoutParams = params

        binding.tvNameBookmarkInfo.text = memberList[position].name
        val formattedText = context.getString(
            R.string.num_bookmark_info,
            memberList[position].bookmarkNum.toString()
        )
        binding.tvNumBookmarkInfo.text = formattedText
        return mBinding!!.root
    }

    override fun getCount(): Int = memberList.size

    override fun getItem(position: Int): Any {
        return memberList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}