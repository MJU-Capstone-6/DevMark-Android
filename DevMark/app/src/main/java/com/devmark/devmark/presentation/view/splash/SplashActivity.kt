package com.devmark.devmark.presentation.view.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devmark.devmark.R
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.SignIn.SignInActivity
import com.devmark.devmark.presentation.view.WorkspaceSelect.SelectWorkspaceActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observer()
        viewModel.doValidation()
    }

    private fun observer(){
        viewModel.loginState.observe(this){
            when(it){
                is UiState.Failure -> {
                    Log.e("SPLASH", it.error ?: "자동 로그인 실패")
                    moveActivity(SignInActivity())
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    Log.d("SPLASH", "자동 로그인 성공")
                    moveActivity(SelectWorkspaceActivity())
                }
            }
        }
    }

    private fun moveActivity(p: Activity) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, p::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}