package com.devmark.devmark.presentation.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.devmark.devmark.R
import com.devmark.devmark.databinding.DialogCustomBinding

class CustomDialog private constructor(private val dialogType: DialogType, private val msg: String?) : DialogFragment() {

    enum class DialogType {
        UNLINK
    }

    companion object {
        @Volatile
        private var instance: CustomDialog? = null

        fun getInstance(dialogType: DialogType, msg: String?): CustomDialog {
            synchronized(this) {
                instance?.dismiss()
                instance = CustomDialog(dialogType, msg)
                return instance!!
            }
        }
    }

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!


    override fun onStart() {
        super.onStart()

        val widthInDp = 330

        val widthInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        dialog?.window?.setLayout(widthInPixels, WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_custom, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        when (dialogType) {
            DialogType.UNLINK -> { setUnlink() }
        }

        return binding.root
    }

    private lateinit var listener : DialogOKClickedListener

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: DialogOKClickedListener {
            override fun onOKClicked(value: String) {
                listener(value)
            }
        }
    }

    interface DialogOKClickedListener {
        fun onOKClicked(value : String)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    private fun setUnlink(){
        binding.dialogTitle.text = getString(R.string.dialog_unlink_title)
        binding.dialogComment.text = getString(R.string.dialog_unlink_comment)
        binding.dialogBtnYes.text = getString(R.string.dialog_unlink_yes)
        binding.dialogBtnNo.text = getString(R.string.dialog_unlink_no)

        binding.dialogBtnNo.setOnClickListener {
            dismiss()
        }
        binding.dialogBtnYes.setOnClickListener {
            listener.onOKClicked("unlink")
            dismiss()
        }
    }
}