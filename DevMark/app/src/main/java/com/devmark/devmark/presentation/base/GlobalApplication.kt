package com.devmark.devmark.presentation.base

import android.app.Application
import com.devmark.devmark.BuildConfig
import com.devmark.devmark.data.repository.UserPreferencesRepositoryImpl
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    companion object {
        lateinit var app: GlobalApplication
    }

    lateinit var userPreferences: UserPreferencesRepositoryImpl

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        app = this
        userPreferences = UserPreferencesRepositoryImpl()
    }
}