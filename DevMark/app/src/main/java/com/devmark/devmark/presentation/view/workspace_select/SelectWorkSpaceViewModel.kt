package com.devmark.devmark.presentation.view.workspace_select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.WorkSpaceRepositoryImpl
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseWorkSpaceCreateEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class SelectWorkSpaceViewModel : ViewModel() {
    private val workSpaceRepositoryImpl = WorkSpaceRepositoryImpl()

    private val _uiState = MutableLiveData<UiState<ResponseWorkSpaceCreateEntity>>(UiState.Loading)
    val uiState: LiveData<UiState<ResponseWorkSpaceCreateEntity>> get() = _uiState

    fun workspaceCreate(title: String, desc: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val accessToken = app.userPreferences.getAccessToken().getOrNull().orEmpty()
            workSpaceRepositoryImpl.createWorkspace(
                accessToken, RequestWorkSpaceCreateEntity(title, desc)
            ).onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Failure(it.message)
            }
        }
    }

}