package com.devmark.devmark.presentation.view.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devmark.devmark.R
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.presentation.utils.PermissionManager
import com.devmark.devmark.presentation.utils.PermissionManager.PERMISSION_REQUEST_CODE
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.signin.SignInActivity
import com.devmark.devmark.presentation.view.workspace_select.SelectWorkspaceActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

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
                    LoggerUtils.error("자동 로그인 실패: ${it.error}")
                    moveActivity(SignInActivity())
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtils.debug("자동 로그인 성공")

                    PermissionManager.checkAndRequestNotificationPermission(this)
//                    fetchFcmRegistrationToken()

                    moveActivity(SelectWorkspaceActivity())
                }
            }
        }
    }

    // 사용 보류
    private fun fetchFcmRegistrationToken(){
        // 현재 토큰을 가져오려면
        // FirebaseMessaging.getInstace().getToken()을 호출한다.
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                LoggerUtils.warning("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            // FCM 등록 토큰 가져오기
            val token = task.result

            val msg = "FCM Registration token: $token"
            LoggerUtils.debug(msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }


    private fun moveActivity(p: Activity) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, p::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "Permission is denied", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Permission is granted", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}