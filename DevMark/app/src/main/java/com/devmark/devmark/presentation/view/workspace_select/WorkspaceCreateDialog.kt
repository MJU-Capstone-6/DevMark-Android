package com.devmark.devmark.presentation.view.workspace_select

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.devmark.devmark.databinding.DialogWorkspaceCreateBinding
import com.devmark.devmark.presentation.utils.GetDisplayUtil

@Suppress("DEPRECATION")
class WorkspaceCreateDialog : DialogFragment() {
    private var _binding: DialogWorkspaceCreateBinding? = null
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
        _binding = DialogWorkspaceCreateBinding.inflate(inflater, container, false)
        val view = binding.root

        // 생성 버튼 동작
        binding.btnDialogCreate.setOnClickListener {
            val workspaceTitle = binding.etDialogAddTitle.text.toString()
            val workspaceDesc = binding.etDialogAddDesc.text.toString()
            if (workspaceTitle.isBlank()) {
                Toast.makeText(requireContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (workspaceDesc.isBlank()) {
                Toast.makeText(requireContext(), "설명을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                buttonClickListener.onButton1Clicked(workspaceTitle, workspaceDesc)
            }
            dismiss()
        }

        // 닫기 버튼 동작
        binding.btnDialogNo.setOnClickListener {
            dismiss()
        }
        return view
    }

    interface OnButtonClickListener {
        fun onButton1Clicked(title: String, desc: String)
    }

    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}