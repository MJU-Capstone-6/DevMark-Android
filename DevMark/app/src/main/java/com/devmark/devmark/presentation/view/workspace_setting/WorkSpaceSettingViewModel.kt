package com.devmark.devmark.presentation.view.workspace_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.WorkSpaceRepositoryImpl
import com.devmark.devmark.domain.model.workspace.ResponseWorkspaceSettingInfoEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class WorkSpaceSettingViewModel : ViewModel() {
    private val workSpaceRepositoryImpl = WorkSpaceRepositoryImpl()

    private val _inviteState = MutableLiveData<UiState<String>>(UiState.Loading)
    val inviteState: LiveData<UiState<String>> get() = _inviteState
    private val _bookmarkCodeState = MutableLiveData<UiState<String>>(UiState.Loading)
    val bookmarkCodeState: LiveData<UiState<String>> get() = _bookmarkCodeState
    private val _uiState = MutableLiveData<UiState<ResponseWorkspaceSettingInfoEntity>>(UiState.Loading)
    val uiState: LiveData<UiState<ResponseWorkspaceSettingInfoEntity>> get() = _uiState

    fun getInviteCode(workspaceId: Int) {
        _inviteState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.getInviteCode(
                app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId
            )
                .onSuccess {
                    _inviteState.value = UiState.Success(it.inviteCode)
                }.onFailure {
                    _inviteState.value = UiState.Failure(it.message)
                }
        }
    }

    fun getBookmarkCode(workspaceId: Int) {
        _bookmarkCodeState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.getBookmarkCode(
                app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId
            )
                .onSuccess {
                    _bookmarkCodeState.value = UiState.Success(it.code)
                }.onFailure {
                    _bookmarkCodeState.value = UiState.Failure(it.message)
                }
        }
    }

    private val _exitState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val exitState: LiveData<UiState<Unit>> get() = _exitState

    fun deleteWorkspace(workspaceId: Int) {
        _exitState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.deleteWorkspace(
                app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId
            )
                .onSuccess {
                    _exitState.value = UiState.Success(Unit)
                }.onFailure {
                    _exitState.value = UiState.Failure(it.message)
                }
        }
    }

    fun getWorkspaceSetting(workspaceId: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.getWorkspaceSettingInfo(
                app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId
            )
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }
                .onFailure {
                    _uiState.value = UiState.Failure(it.message)
                }
        }
    }
}