package com.devmark.devmark.presentation.view.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.BookmarkRepositoryImpl
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class BookmarkViewModel: ViewModel() {
    private val bookmarkRepositoryImpl = BookmarkRepositoryImpl()


    private val _detailState = MutableLiveData<UiState<BookmarkDetailEntity>>(UiState.Loading)
    val detailState: LiveData<UiState<BookmarkDetailEntity>> get() = _detailState

    fun fetchData(bookmarkId: Int){
        _detailState.value = UiState.Loading

        viewModelScope.launch {
            bookmarkRepositoryImpl.getBookmarkDetail(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkId)
                .onSuccess {
                    _detailState.value = UiState.Success(it)
                }.onFailure {
                    _detailState.value = UiState.Failure(it.message)
                }
        }
    }


    private val _commentState = MutableLiveData<UiState<List<CommentEntity>>>(UiState.Loading)
    val commentState: LiveData<UiState<List<CommentEntity>>> get() = _commentState

    fun fetchComment(bookmarkId: Int){
        _commentState.value = UiState.Loading

        viewModelScope.launch {
            bookmarkRepositoryImpl.getComments(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkId)
                .onSuccess {
                    _commentState.value = UiState.Success(it)
                }.onFailure {
                    _commentState.value = UiState.Failure(it.message)
                }
        }
    }
}