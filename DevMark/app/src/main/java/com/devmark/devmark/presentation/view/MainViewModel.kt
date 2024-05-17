package com.devmark.devmark.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.WorkSpaceRepositoryImpl
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var workspaceId: Int = -1
    var workspaceName: String = ""
    var workspaceDescription: String = ""
    val workSpaceRepositoryImpl = WorkSpaceRepositoryImpl()
    var categoryList = listOf<Pair<Int, String>>()
    var memberList = listOf<Pair<Int, String>>()

    private val _filterState =
        MutableLiveData<UiState<Pair<List<Pair<Int, String>>, List<Pair<Int, String>>>>>(UiState.Loading)
    val filterState: LiveData<UiState<Pair<List<Pair<Int, String>>, List<Pair<Int, String>>>>> get() = _filterState

    fun fetchInfo() {
        _filterState.value = UiState.Loading

        viewModelScope.launch {
            workSpaceRepositoryImpl.getWorkspaceInfo(
                app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId
            ).onSuccess {
                memberList = it.users
                categoryList = it.categories
                _filterState.value = UiState.Success(
                    Pair(it.users, it.categories)
                )
            }.onFailure {
                _filterState.value = UiState.Failure(it.message)
            }
        }
    }


}