package com.devmark.devmark.presentation.view.workspace_select

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.devmark.devmark.databinding.DialogWorkspaceJoinBinding
import com.devmark.devmark.presentation.utils.GetDisplayUtil


class WorkspaceJoinDialog : DialogFragment() {
    private var _binding: DialogWorkspaceJoinBinding? = null
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
        _binding = DialogWorkspaceJoinBinding.inflate(inflater, container, false)

        binding.btnInviteCodeYes.setOnClickListener {
            val inviteCode = binding.etInviteCode.text.toString()
            if (inviteCode.isBlank()) {
                Toast.makeText(requireContext(), "코드를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                buttonClickListener.onButton1Clicked(inviteCode)
            }
            dismiss()
        }

        binding.btnInviteCodeNo.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    interface OnButtonClickListener {
        fun onButton1Clicked(code: String)
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