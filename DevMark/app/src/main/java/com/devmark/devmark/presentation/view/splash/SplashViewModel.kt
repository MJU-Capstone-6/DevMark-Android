package com.devmark.devmark.presentation.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.UserRepositoryImpl
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashViewModel: ViewModel() {
    private val userRepositoryImpl = UserRepositoryImpl()

    private val _loginState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val loginState: LiveData<UiState<Unit>> get() = _loginState

    fun doValidation(){
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            userRepositoryImpl.login(app.userPreferences.getAccessToken().getOrNull().orEmpty()
            ).onSuccess {
                runBlocking(Dispatchers.IO){
                    app.userPreferences.setAccessToken(it.accessToken)
                    app.userPreferences.setRefreshToken(it.refreshToken)
                }

                validationKakao()
            }.onFailure {
                _loginState.value = UiState.Failure(it.message)
            }
        }
    }

    private fun validationKakao(){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    _loginState.value = UiState.Failure(error.message)
                } else {
                    _loginState.value = UiState.Success(Unit)
                }
            }
        } else {
            _loginState.value = UiState.Failure("카카오 로그인 필요")
        }
    }
}