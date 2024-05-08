package com.devmark.devmark

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.devmark.devmark.databinding.ActivitySplashBinding
import com.devmark.devmark.presentation.SignIn.SignInActivity
import com.devmark.devmark.presentation.WorkspaceSelect.SelectWorkspaceActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding 세팅
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)


        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        moveActivity(SignInActivity())
                    } else {
                        // 기타 에러
                        moveActivity(SignInActivity())
                    }
                } else {
                    // 이미 로그인 토큰이 존재한다면
                    moveActivity(SelectWorkspaceActivity())
                }
            }
        } else {
            //로그인 필요
            moveActivity(SignInActivity())
        }
    }

    fun moveActivity(p: Activity) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, p::class.java)
            startActivity(intent)
        }, 1000)
    }
}