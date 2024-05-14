package com.devmark.devmark.data.model.user

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient


class KakaoAuthService(private val context: Context) {

    companion object {
        const val KAKAO_TALK = "카카오톡"
        const val KAKAO_ACCOUNT = "카카오계정"
        const val KAKAO_ACCESS_TOKEN = "카카오 Access Token"
    }

    private val client = UserApiClient.instance

    private val isKakaoTalkLoginAvailable: Boolean
        get() = client.isKakaoTalkLoginAvailable(context)

    fun signInKakao(
        loginListener: ((String) -> Unit)
    ) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                signInError(error)
            } else if (token != null) signInSuccess(token, loginListener)
        }

        if (isKakaoTalkLoginAvailable) {
            client.loginWithKakaoTalk(context, callback = callback)
        } else {
            client.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun signInError(throwable: Throwable) {
        val kakaoType = if (isKakaoTalkLoginAvailable) KAKAO_TALK else KAKAO_ACCOUNT
         Log.e("LOGIN", "{$kakaoType}으로 로그인 실패 ${throwable.message}")
    }

    private fun signInSuccess(
        oAuthToken: OAuthToken,
        loginListener: ((String) -> Unit)
    ) {
        Log.d("LOGIN", "$KAKAO_ACCESS_TOKEN ${oAuthToken.accessToken}")
        client.me { _, error ->
            loginListener(oAuthToken.accessToken)
            if (error != null) {
                Log.e("LOGIN", "사용자 정보 요청 실패 $error")
            }
        }
    }
}