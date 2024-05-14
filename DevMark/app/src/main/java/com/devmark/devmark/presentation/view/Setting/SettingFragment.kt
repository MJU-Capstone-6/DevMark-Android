package com.devmark.devmark.presentation.view.Setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.databinding.FragmentSettingBinding
import com.devmark.devmark.presentation.view.SignIn.SignInActivity
import com.devmark.devmark.presentation.view.WorkspaceSelect.SelectWorkspaceActivity
import com.kakao.sdk.user.UserApiClient

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val TAG = "SignOut"
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))
            startActivity(intent)
        }

        // 로그아웃 버튼 눌렀을 때
        binding.ibSignout.setOnClickListener {
            val dialog = SignOutDialog("로그아웃 하시겠습니까?")
            dialog.setButtonClickListener(object : SignOutDialog.OnButtonClickListener {
                override fun onButton1Clicked() {
                    // 예 버튼을 눌렀을 때 처리할 곳
                    // 로그아웃
                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                        } else {
                            Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")

                            if (requireActivity().javaClass.simpleName == "MainActivity") {
                                (requireActivity() as MainActivity).moveActivity(SignInActivity())
                            } else {
                                (requireActivity() as SelectWorkspaceActivity).moveActivity(
                                    SignInActivity()
                                )

                            }
                        }
                    }
                }

                override fun onButton2Clicked() {
                    // 아니요 버튼을 눌렀을 때 처리할 곳
                    dialog.dismiss()
                }
            })
            dialog.show(parentFragmentManager, "SignOut Dialog")
        }

        // 회원탈퇴 버튼을 눌렀을 때
        binding.ibWithdrawal.setOnClickListener {
            val dialog = SignOutDialog("회원탈퇴 하시겠습니까?")
            dialog.setButtonClickListener(object : SignOutDialog.OnButtonClickListener {
                override fun onButton1Clicked() {
                    // 예 버튼을 눌렀을 때 처리할 곳
                }

                override fun onButton2Clicked() {
                    // 아니요 버튼을 눌렀을 때 처리할 곳
                    dialog.dismiss()
                }
            })
            dialog.show(parentFragmentManager, "WithDrawal Dialog")
        }

        return binding.root
    }
}