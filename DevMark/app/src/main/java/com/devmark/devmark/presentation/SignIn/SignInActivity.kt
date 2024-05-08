package com.devmark.devmark.presentation.SignIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.devmark.devmark.R
import com.devmark.devmark.SplashActivity
import com.devmark.devmark.databinding.ActivitySigninBinding
import com.devmark.devmark.presentation.WorkspaceSelect.SelectWorkspaceActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private val splashActivity = SplashActivity()
    private var backPressedTime: Long = 0
    private val TAG = "SignIn"
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패 ", error)
        } else if (token != null) {
            // 최종적으로 카카오로그인 및 유저정보 가져온 결과
            Log.d(TAG, "카카오계정으로 로그인 성공 ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
        addOnBackPressedCallback()

        val kakaoLoginButton: Button = findViewById(R.id.btn_kakao_login)
        kakaoLoginButton.setOnClickListener {
            //로그인 함수 호출
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨


        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패 ", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.d(TAG, "카카오톡으로 로그인 성공 ")

                    val intent = Intent(this, SelectWorkspaceActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            // 카카오계정으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.d(TAG, "카카오계정으로 로그인 성공")

                    val intent = Intent(this, SelectWorkspaceActivity::class.java)
                    startActivity(intent)
                }
            }
        }
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
