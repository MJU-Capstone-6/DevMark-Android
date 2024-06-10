package com.devmark.devmark.presentation.view.recommend

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.devmark.devmark.R
import com.devmark.devmark.databinding.ItemTopCategoryBinding
import com.devmark.devmark.domain.model.workspace.TopCategory
import com.devmark.devmark.presentation.utils.capitalizeFirstLetter

class TopCategoryLvAdapter(val context: Context, private var categoryList: List<TopCategory>) :
    BaseAdapter() {
    private var mBinding: ItemTopCategoryBinding? = null
    private val binding get() = mBinding!!

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        mBinding =
            ItemTopCategoryBinding.inflate(LayoutInflater.from(parent?.context))

        binding.tvTopCategoryName.text = categoryList[position].name.capitalizeFirstLetter()
        val formattedText = context.getString(
            R.string.num_mybookmark_category,
            categoryList[position].bookmarkCount.toString()
        )
        binding.tvTopCategoryNum.text = formattedText
        return mBinding!!.root
    }

    override fun getCount(): Int = categoryList.size

    override fun getItem(position: Int): Any {
        return categoryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(newList: List<TopCategory>){
        categoryList = newList
        notifyDataSetChanged()
    }
}