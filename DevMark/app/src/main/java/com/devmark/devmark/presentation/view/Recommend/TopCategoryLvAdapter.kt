package com.devmark.devmark.presentation.view.Recommend

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.devmark.devmark.R
import com.devmark.devmark.domain.model.TopCategory
import com.devmark.devmark.databinding.ItemTopCategoryBinding

class TopCategoryLvAdapter(val context: Context, private val categoryList: List<TopCategory>) :
    BaseAdapter() {
    private var mBinding: ItemTopCategoryBinding? = null
    private val binding get() = mBinding!!

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        mBinding =
            ItemTopCategoryBinding.inflate(LayoutInflater.from(parent?.context))

        binding.tvTopCategoryName.text = categoryList[position].category
        val formattedText = context.getString(
            R.string.num_mybookmark_category,
            categoryList[position].num.toString()
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
}