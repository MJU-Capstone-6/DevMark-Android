package com.devmark.devmark.presentation.view.workspace_select

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.devmark.devmark.databinding.DialogWorkspaceCreateBinding

class WorkspaceCreateDialog : DialogFragment() {
    private var _binding: DialogWorkspaceCreateBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        super.onStart()

        // Dialog의 window 속성 조정
        val window = dialog?.window
        val displayMetrics = DisplayMetrics()
        context?.display?.getRealMetrics(displayMetrics)

        val layoutParams = window?.attributes

        val width = (displayMetrics.widthPixels * 0.8).toInt()

        layoutParams?.width = width
        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogWorkspaceCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

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