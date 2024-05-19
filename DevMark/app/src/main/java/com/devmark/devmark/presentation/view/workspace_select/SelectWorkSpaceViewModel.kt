package com.devmark.devmark.presentation.view.workspace_select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.UserRepositoryImpl
import com.devmark.devmark.data.repository.WorkSpaceRepositoryImpl
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class SelectWorkSpaceViewModel : ViewModel() {
    private val workSpaceRepositoryImpl = WorkSpaceRepositoryImpl()
    private val userRepositoryImpl = UserRepositoryImpl()

    private val _uiState = MutableLiveData<UiState<List<WorkspaceEntity>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<WorkspaceEntity>>> get() = _uiState

    private var _currentWorkspace = MutableLiveData<WorkspaceEntity>()
    val currentWorkspace get() = _currentWorkspace

    fun fetchData() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val accessToken = app.userPreferences.getAccessToken().getOrNull().orEmpty()
            val currentWorkspace = app.userPreferences.getCurrentWorkspace().getOrNull()
            userRepositoryImpl.getWorkspaceList(accessToken)
                .onSuccess {
                    val (newList, currentWorkspaceList) = it.workspaces.partition { item -> item.id != currentWorkspace}
                    if(currentWorkspaceList.isNotEmpty()) _currentWorkspace.value = currentWorkspaceList.first()
                    _uiState.value = UiState.Success(newList)
                }.onFailure {
                    _uiState.value = UiState.Failure(it.message)
                }
        }
    }

    private val _createState =
        MutableLiveData<UiState<WorkspaceEntity>>(UiState.Loading)
    val createState: LiveData<UiState<WorkspaceEntity>> get() = _createState

    fun workspaceCreate(title: String, desc: String) {
        _createState.value = UiState.Loading

        viewModelScope.launch {
            val accessToken = app.userPreferences.getAccessToken().getOrNull().orEmpty()
            workSpaceRepositoryImpl.createWorkspace(
                accessToken, RequestWorkSpaceCreateEntity(title, desc)
            ).onSuccess {
                _createState.value = UiState.Success(it)
            }.onFailure {
                _createState.value = UiState.Failure(it.message)
            }
        }
    }

    fun workspaceJoin(inviteCode: String){
        _createState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.joinWorkspace(app.userPreferences.getAccessToken().getOrNull().orEmpty(), inviteCode)
                .onSuccess {
                    _createState.value = UiState.Success(it)
                }.onFailure {
                    _createState.value = UiState.Failure(it.message)
                }
        }
    }
}