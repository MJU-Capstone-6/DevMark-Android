package com.devmark.devmark.presentation.view.bookmark

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.devmark.devmark.R
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.databinding.DialogUpdateCategoryBinding
import com.devmark.devmark.presentation.utils.GetDisplayUtil

class UpdateCategoryDialog(private val items: List<String>) : DialogFragment() {
    private var _binding: DialogUpdateCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()

        val size = GetDisplayUtil.getSize(requireContext())
        val width = (size.first * 0.8).toInt()

        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogUpdateCategoryBinding.inflate(inflater, container, false)

        val autoAdapter = CategoryAutoCompleteViewAdapter(requireContext(), R.layout.item_dropdown_category, items)
        binding.etAuto.setDropDownBackgroundResource(R.drawable.shape_dropdown_category)
        binding.etAuto.setAdapter(autoAdapter)

        binding.dialogBtnDone.setOnClickListener{
            dismiss()
        }

        binding.dialogBtnSave.setOnClickListener{
            buttonClickListener.onButtonClicked(binding.etAuto.text.toString())
            LoggerUtils.info(binding.etAuto.text.toString())
            dismiss()
        }
        
        return binding.root
    }

    interface OnButtonClickListener {
        fun onButtonClicked(categoryName: String)
    }

    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    private lateinit var buttonClickListener: OnButtonClickListener

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}