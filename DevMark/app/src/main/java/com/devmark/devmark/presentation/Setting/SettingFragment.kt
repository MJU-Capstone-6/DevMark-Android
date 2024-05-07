package com.devmark.devmark.presentation.Setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devmark.devmark.MainActivity
import com.devmark.devmark.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // BottomNav 출력
        if (requireActivity().javaClass.simpleName == "MainActivity") {
            (requireActivity() as MainActivity).changeNaviVisibility(false)
        }

        binding = FragmentSettingBinding.inflate(layoutInflater)
        binding.ibBackSetting.setOnClickListener {
            // 이전 화면으로 뒤로가기
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.ibTermsConditions.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))
            startActivity(intent)
        }

        return binding.root
    }
}