package com.devmark.devmark.presentation.view.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.data.repository.UserRepositoryImpl
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignInViewModel : ViewModel() {
    private val userRepositoryImpl = UserRepositoryImpl()

    private val _loginState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val loginState: LiveData<UiState<Unit>> get() = _loginState

    fun login(accessToken: String) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            userRepositoryImpl.login(
                accessToken
            ).onSuccess {
                runBlocking(Dispatchers.IO) {
                    app.userPreferences.setAccessToken(it.accessToken)
                    app.userPreferences.setRefreshToken(it.refreshToken)
                }

                _loginState.value = UiState.Success(Unit)
            }.onFailure {
                _loginState.value = UiState.Failure(it.message)
            }
        }
    }
}