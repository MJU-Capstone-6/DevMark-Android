package com.devmark.devmark.presentation.view.remind

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.UserRepositoryImpl
import com.devmark.devmark.domain.model.user.NotificationEntity
import com.devmark.devmark.presentation.base.GlobalApplication
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class RemindViewModel : ViewModel() {
    private val repositoryImpl = UserRepositoryImpl()

    private var _uiState = MutableLiveData<UiState<List<NotificationEntity>>>(UiState.Loading)
    val uiState get() = _uiState

    fun fetchData() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            repositoryImpl.getNotificationList(
                GlobalApplication.app.userPreferences.getAccessToken().getOrNull().orEmpty()
            )
                .onSuccess { response ->
                    _uiState.value = UiState.Success(response)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Failure(error.message)
                }
        }
    }
}