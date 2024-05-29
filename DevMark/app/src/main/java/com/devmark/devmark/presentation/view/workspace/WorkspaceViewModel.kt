package com.devmark.devmark.presentation.view.workspace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.BookmarkRepositoryImpl
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.presentation.base.GlobalApplication
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class WorkspaceViewModel : ViewModel() {
    private val bookmarkRepositoryImpl = BookmarkRepositoryImpl()

    private val _uiState = MutableLiveData<UiState<List<BookmarksEntity>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<BookmarksEntity>>> get() = _uiState

    fun fetchData(userIds: List<Int>, categoryIds: List<Int>) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val accessToken =
                GlobalApplication.app.userPreferences.getAccessToken().getOrNull().orEmpty()
            val currentWorkspace =
                GlobalApplication.app.userPreferences.getCurrentWorkspace().getOrNull()
            bookmarkRepositoryImpl.getBookmarks(
                accessToken, currentWorkspace ?: -1,
                userIds.joinToString(","), categoryIds.joinToString(",")
            )
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onFailure {
                    _uiState.value = UiState.Failure(it.message)
                }
        }
    }
}