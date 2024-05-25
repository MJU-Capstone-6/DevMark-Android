package com.devmark.devmark.presentation.view.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.BookmarkRepositoryImpl
import com.devmark.devmark.data.repository.CommentRepositoryImpl
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.userId
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {

    private val bookmarkRepository = BookmarkRepositoryImpl()
    private val commentRepository = CommentRepositoryImpl()

    private lateinit var bookmarkInfo: BookmarkDetailEntity

    private val _detailState = MutableLiveData<UiState<BookmarkDetailEntity>>(UiState.Loading)
    val detailState: LiveData<UiState<BookmarkDetailEntity>> get() = _detailState

    private val _updateState = MutableLiveData<UiState<BookmarksEntity>>(UiState.Loading)
    val updateState: LiveData<UiState<BookmarksEntity>> get() = _updateState

    private val _commentState = MutableLiveData<UiState<List<CommentEntity>>>(UiState.Loading)
    val commentState: LiveData<UiState<List<CommentEntity>>> get() = _commentState



    fun fetchData(bookmarkId: Int) {
        _detailState.value = UiState.Loading

        viewModelScope.launch {
            bookmarkRepository.getBookmarkDetail(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkId)
            .onSuccess {
                bookmarkInfo = it
                _detailState.value = UiState.Success(it)
            }.onFailure {
                _detailState.value = UiState.Failure(it.message)
            }
        }
    }

    fun updateCategory(newCategoryId: Int) {
        if (newCategoryId == -1) {
            _updateState.value = UiState.Failure("Invalid category ID: -1")
            return
        }

        _updateState.value = UiState.Loading

        viewModelScope.launch {
            val bookmark = bookmarkInfo.toUpdateBookmarkEntity(newCategoryId)

            bookmarkRepository.updateBookmark(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkInfo.id, bookmark)
                .onSuccess {
                    bookmarkInfo.updateCategoryInfo(it.categoryId)
                    _updateState.value = UiState.Success(it)
                }.onFailure {
                    _updateState.value = UiState.Failure(it.message)
                }
        }
    }

    fun fetchComment(bookmarkId: Int) {
        _commentState.value = UiState.Loading
        viewModelScope.launch {
            bookmarkRepository.getComments(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkId)
                .onSuccess {
                    _commentState.value = UiState.Success(it)
                }.onFailure {
                    _commentState.value = UiState.Failure(it.message)
                }
        }
    }

    private fun BookmarkDetailEntity.toUpdateBookmarkEntity(newCategoryId: Int): UpdateBookmarkEntity {
        return UpdateBookmarkEntity(
            categoryId = newCategoryId,
            link = link,
            summary = summary ?: "",
            title = title,
            userId = userId,
            workspaceId = workspaceId
        )
    }

    private fun BookmarkDetailEntity.updateCategoryInfo(categoryId: Int) {
        this.categoryId = categoryId
        this.categoryName = categoryId.toString()
    }

    fun postComment(bookmarkId: Int, context: String){
        viewModelScope.launch {
            commentRepository.postComment(app.userPreferences.getAccessToken().getOrNull().orEmpty(), bookmarkId, context)
                .onSuccess {
                    fetchComment(bookmarkId)
                }.onFailure {
                    _commentState.value = UiState.Failure(it.message)
                }
        }
    }

    fun updateComment(bookmarkId: Int, commentId: Int, context: String){
        viewModelScope.launch {
            commentRepository.updateComment(app.userPreferences.getAccessToken().getOrNull().orEmpty(), context, commentId)
                .onSuccess {
                    fetchComment(bookmarkId)
                }.onFailure {
                    _commentState.value = UiState.Failure(it.message)
                }
        }
    }

    fun deleteComment(bookmarkId: Int, commentId: Int){
        viewModelScope.launch {
            commentRepository.deleteComment(app.userPreferences.getAccessToken().getOrNull().orEmpty(), commentId)
                .onSuccess {
                    fetchComment(bookmarkId)
                }.onFailure {
                    _commentState.value = UiState.Failure(it.message)
                }
        }
    }
}
