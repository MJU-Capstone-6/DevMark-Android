package com.devmark.devmark.presentation.view.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.BookmarkRepositoryImpl
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.userId
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class BookmarkViewModel: ViewModel() {
    private val bookmarkRepositoryImpl = BookmarkRepositoryImpl()
    private lateinit var bookmarkInfo: BookmarkDetailEntity

    private val _detailState = MutableLiveData<UiState<BookmarkDetailEntity>>(UiState.Loading)
    val detailState: LiveData<UiState<BookmarkDetailEntity>> get() = _detailState

    fun fetchData(bookmarkId: Int){
        _detailState.value = UiState.Loading

        viewModelScope.launch {
            bookmarkRepositoryImpl.getBookmarkDetail(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkId)
                .onSuccess {
                    bookmarkInfo = it
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

    private val _updateState = MutableLiveData<UiState<BookmarksEntity>>(UiState.Loading)
    val updateState: LiveData<UiState<BookmarksEntity>> get() = _updateState

    fun updateCategory(newCategoryId: Int){
        _updateState.value = UiState.Loading

        if(newCategoryId == -1) {
            _updateState.value = UiState.Failure("-1")
            return
        }

        viewModelScope.launch {
            val bookmark = bookmarkInfo.run {
                UpdateBookmarkEntity(
                    categoryId = newCategoryId,
                    link = link,
                    summary = summary ?: "",
                    title = title,
                    userId = userId,
                    workspaceId = workspaceId
                )
            }

            bookmarkRepositoryImpl.updateBookmark(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkInfo.id, bookmark)
                .onSuccess {
                    bookmarkInfo.categoryId = it.categoryId
                    bookmarkInfo.categoryName = it.categoryId.toString() // todo API 수정 요청함 category_name

                    _updateState.value = UiState.Success(it)
                }.onFailure {
                    _updateState.value = UiState.Failure(it.message)
                }
        }
    }
}