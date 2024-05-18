package com.devmark.devmark.presentation.view.workspace_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.WorkSpaceRepositoryImpl
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class WorkSpaceSettingViewModel: ViewModel() {
    private val workSpaceRepositoryImpl = WorkSpaceRepositoryImpl()

    private val _inviteState = MutableLiveData<UiState<String>>(UiState.Loading)
    val inviteState: LiveData<UiState<String>> get() = _inviteState

    fun getInviteCode(workspaceId: Int){
        _inviteState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.getInviteCode(app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId)
                .onSuccess {
                    _inviteState.value = UiState.Success(it.inviteCode)
                }.onFailure {
                    _inviteState.value = UiState.Failure(it.message)
                }
        }
    }
}