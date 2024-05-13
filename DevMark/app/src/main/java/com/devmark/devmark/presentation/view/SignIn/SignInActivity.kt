package com.devmark.devmark.presentation.view.SignIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devmark.devmark.presentation.view.splash.SplashActivity
import com.devmark.devmark.data.model.user.KakaoAuthService
import com.devmark.devmark.databinding.ActivitySigninBinding
import com.devmark.devmark.presentation.utils.UiState


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private val viewModel: SignInViewModel by viewModels()
    private var backPressedTime: Long = 0
    private lateinit var kakaoAuthService: KakaoAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addOnBackPressedCallback()

        kakaoAuthService = KakaoAuthService(this)
        observer()

        binding.btnKakaoLogin.setOnClickListener {
            kakaoAuthService.signInKakao(viewModel::login)
        }
    }

    private fun observer(){
        viewModel.loginState.observe(this){
            when(it){
                is UiState.Failure -> {
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    Log.e("SIGNIN", "로그인 실패: ${it.error}")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    moveToSplash()
                }
            }
        }
    }

    private fun moveToSplash(){
        Log.i("SIGNIN", "moveToSplash()")
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }


    // 뒤로가기로 스플래시 화면으로 가는 것 방지
    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= 2000) {
                    backPressedTime = System.currentTimeMillis()
                    Toast.makeText(this@SignInActivity, "한번 더 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT)
                        .show()
                } else if (System.currentTimeMillis() - backPressedTime < 2000) {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }
}
