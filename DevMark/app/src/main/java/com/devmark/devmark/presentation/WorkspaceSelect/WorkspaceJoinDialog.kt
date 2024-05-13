package com.devmark.devmark.presentation.WorkspaceSelect

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.devmark.devmark.databinding.DialogWorkspaceJoinBinding

@Suppress("DEPRECATION")
class WorkspaceJoinDialog : DialogFragment() {
    private var _binding: DialogWorkspaceJoinBinding? = null
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
        _binding = DialogWorkspaceJoinBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        // 예 버튼 동작
        binding.btnInviteCodeYes.setOnClickListener {
            buttonClickListener.onButton1Clicked()
        }

        // 아니요 버튼 동작
        binding.btnInviteCodeNo.setOnClickListener {
            buttonClickListener.onButton2Clicked()
        }

        return view
    }

    interface OnButtonClickListener {
        fun onButton1Clicked()
        fun onButton2Clicked()
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